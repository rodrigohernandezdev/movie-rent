package com.test.movierent.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception Class when any object not exist
 **/

@Getter
@Setter
public class NotExistException extends RuntimeException {
    private final String message;

    public NotExistException(String message) {
        this.message = message;
    }
}
