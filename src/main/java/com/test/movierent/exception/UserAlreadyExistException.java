package com.test.movierent.exception;

import lombok.Data;

import java.util.Objects;

/**
 *  Custom Exception Class for management registration if users exists
 **/

@Data
public class UserAlreadyExistException extends RuntimeException {
    private String message;

    public UserAlreadyExistException(String message){
        this.message = message;
    }
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
