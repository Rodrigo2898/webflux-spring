package com.reactive.reactiveflash.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static  java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.CLASS)
@Constraint(validatedBy = {MongoIdValidator.class})
public @interface MongoId {

    String message() default  "{com.reactive.reactiveflash.MongoId.message}";

    Class<?>[] groups() default {  };

    Class<? extends Payload>[] payload() default {  };
}
