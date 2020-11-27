package com.test.movierent.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception Class for problems with the token used in registration or recovery
 **/

@Getter
@Setter
public class TokenException extends RuntimeException {
    private final String message;
    private final Integer type;


    public TokenException(String message, Integer type) {
        this.message = message;
        this.type = type;
    }
}
