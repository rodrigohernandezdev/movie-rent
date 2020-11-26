package com.test.movierent.service;

import com.test.movierent.exception.NotExistException;
import com.test.movierent.model.Movie;

public interface UserMovieService {
    void like(Long movieId) throws NotExistException;
    Movie rent(Long movieId);
}
