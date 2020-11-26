package com.test.movierent.exception;

import lombok.Data;

import java.util.Objects;

/**
 * Custom Exception Class when any object not exist
 **/

@Data
public class NotExistException extends RuntimeException {
    private String message;

    public NotExistException(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
