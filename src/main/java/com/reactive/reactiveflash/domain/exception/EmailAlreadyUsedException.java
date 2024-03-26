package com.reactive.reactiveflash.domain.exception;

public class EmailAlreadyUsedException extends ReactiveFlashcardsException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
