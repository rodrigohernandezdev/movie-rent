package com.test.movierent.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *  Custom Exception Class for management registration if users exists
 **/

@Getter
@Setter
public class UserAlreadyExistException extends RuntimeException {
    private final String message;

    public UserAlreadyExistException(String message){
        this.message = message;
    }

}
