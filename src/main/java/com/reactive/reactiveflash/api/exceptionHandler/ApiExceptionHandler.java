package com.reactive.reactiveflash.api.exceptionHandler;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.reactiveflash.api.controller.response.ProblemResponse;
import com.reactive.reactiveflash.domain.exception.BaseErrorMessage;
import com.reactive.reactiveflash.domain.exception.ReactiveFlashcardsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@Slf4j
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

    private final ReactiveFlashCardsHandler reactiveFlashCardsHandler;
    private final MethodNotAllowHandler methodNotAllowHandler;
    private final JsonProcessingHandler jsonProcessingHandler;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(ReactiveFlashcardsException.class, e -> reactiveFlashCardsHandler.handlerException(exchange, e))
                .onErrorResume(MethodNotAllowedException.class, e -> methodNotAllowHandler.handlerException(exchange, e))
                .onErrorResume(JsonGenerationException.class, e -> jsonProcessingHandler.handlerException(exchange, e))
                .then();
    }
}
