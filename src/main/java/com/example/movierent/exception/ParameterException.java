package com.example.movierent.exception;

import lombok.Data;

import java.util.Objects;

/**
 * Custom Exception Class for problems with the parameters received
 **/

@Data
public class ParameterException extends RuntimeException {
    private String message;

    public ParameterException(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}
