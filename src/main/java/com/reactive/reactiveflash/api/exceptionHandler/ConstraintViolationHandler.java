package com.reactive.reactiveflash.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.reactiveflash.api.controller.response.ErrorFieldResponse;
import com.reactive.reactiveflash.api.controller.response.ProblemResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.reactiveflash.domain.exception.BaseErrorMessage.*;
import static org.springframework.http.HttpStatus.*;
@Slf4j
@Component
public class ConstraintViolationHandler extends AbstractHandleException<ConstraintViolationException> {
    public ConstraintViolationHandler(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, ConstraintViolationException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, BAD_REQUEST);
            return GENERIC_BAD_REQUEST.getMessage();
        }).map(message -> buildError(BAD_REQUEST, message))
                .flatMap(response -> buildResquestErrorMessage(response, ex))
                .doFirst(() -> log.error("=== ConstraintViolationException", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }

    private Mono<ProblemResponse> buildResquestErrorMessage(ProblemResponse response, ConstraintViolationException ex) {
        return Flux.fromIterable(ex.getConstraintViolations())
                .map(constraintViolation -> ErrorFieldResponse.builder()
                        .name(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString())
                        .message(constraintViolation.getMessage())
                        .build())
                .collectList()
                .map(problems -> response.toBuilder().fields(problems).build());
    }
}
