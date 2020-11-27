package com.test.movierent.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom Exception Class when there are a problem with a management movie and a user.
 * @apiNote type property is 1-rent 2-buy 3-anyone
 **/

@Getter
@Setter
public class UserMovieException extends RuntimeException {
    private final String message;
    private final Integer type;

    public UserMovieException(String message, Integer type) {
        this.message = message;
        this.type = type;
    }

}
