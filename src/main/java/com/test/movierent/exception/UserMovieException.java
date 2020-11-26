package com.test.movierent.exception;

import lombok.Data;

import java.util.Objects;

/**
 * Custom Exception Class when there are a problem with a management movie and a user.
 * @apiNote type property is 1-rent 2-buy 3-anyone
 **/

@Data
public class UserMovieException extends RuntimeException {
    private String message;
    private Integer type;

    public UserMovieException(String message, Integer type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}
