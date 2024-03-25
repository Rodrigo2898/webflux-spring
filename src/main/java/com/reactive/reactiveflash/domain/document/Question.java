package com.reactive.reactiveflash.domain.document;

public record Question(
        String asked,
        String answered,
        String expected
) {
}