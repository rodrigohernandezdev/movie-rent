package com.test.movierent.exception;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom Exception Class for problems with the parameters received
 **/

@Getter
@Setter
public class ParameterException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ParameterException.class);
    private final String message;

    public ParameterException(String message) {
        this.message = message;
    }

}
