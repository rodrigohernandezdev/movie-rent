package com.test.movierent.exception;

import lombok.Data;

import java.util.Objects;

/**
 * Custom Exception Class when can not be created
 **/

@Data
public class NotCreatedException extends RuntimeException {
    private String message;

    public NotCreatedException(String message) {
        this.message = message;
    }
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
