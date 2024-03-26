package com.reactive.reactiveflash.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.reactiveflash.domain.exception.BaseErrorMessage;
import com.reactive.reactiveflash.domain.exception.ReactiveFlashcardsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class ReactiveFlashCardsHandler extends AbstractHandleException<ReactiveFlashcardsException> {
    public ReactiveFlashCardsHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, ReactiveFlashcardsException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, INTERNAL_SERVER_ERROR);
            return BaseErrorMessage.GENERIC_EXCEPTION.getMessage();})
                .map((message) -> buildError(INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("=== ReactiveFlashcardsException ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
