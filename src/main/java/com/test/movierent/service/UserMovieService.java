package com.test.movierent.service;

import com.test.movierent.model.MovieUserRentBuy;

public interface UserMovieService {
    void like(Long movieId);
    MovieUserRentBuy rent(Long movieId, Integer quantity);
    MovieUserRentBuy buy(Long movieId, Integer quantity);
    MovieUserRentBuy findByUserAndMovieId(Long movieId);
    MovieUserRentBuy returnMovie(MovieUserRentBuy returnMovie);
}
