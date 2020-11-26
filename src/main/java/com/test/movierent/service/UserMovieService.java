package com.test.movierent.service;

import com.test.movierent.exception.NotExistException;
import com.test.movierent.exception.UserMovieException;
import com.test.movierent.model.Movie;
import com.test.movierent.model.MovieUserRentBuy;

public interface UserMovieService {
    void like(Long movieId) throws NotExistException;
    MovieUserRentBuy rent(Long movieId, Integer quantity) throws UserMovieException;
    MovieUserRentBuy buy(Long movieId, Integer quantity) throws UserMovieException;
    MovieUserRentBuy findByUserAndMovieId(Long movieId);
    MovieUserRentBuy returnMovie(MovieUserRentBuy returnMovie);
}
