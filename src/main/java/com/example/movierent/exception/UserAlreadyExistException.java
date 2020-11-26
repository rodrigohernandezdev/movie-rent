package com.example.movierent.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
