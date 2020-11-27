package com.test.movierent.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception Class when can not be created
 **/

@Getter
@Setter
public class NotCreatedException extends RuntimeException {
    private final String message;

    public NotCreatedException(String message) {
        this.message = message;
    }
}
