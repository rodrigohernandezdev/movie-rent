package com.example.movierent.exception;

import lombok.Data;

import java.util.Objects;

/**
 * Custom Exception Class for problems with the token used in registration or recovery
 **/

@Data
public class TokenException extends RuntimeException {
    private String message;
    private Integer type;

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public TokenException(String message, Integer type) {
        this.message = message;
        this.type = type;
    }
}
