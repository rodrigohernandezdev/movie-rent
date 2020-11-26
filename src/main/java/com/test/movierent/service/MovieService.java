package com.test.movierent.service;

import com.test.movierent.exception.NotCreatedException;
import com.test.movierent.model.Movie;
import com.test.movierent.model.dto.MovieDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Movie findByMovieId(Long movieId);
    Movie findByNameMovie(String nameMovie);
    Page<Movie> findAll(Pageable pageable);
    Page<Movie> findAllByAvailability(Boolean availability, Pageable pageable);
    Movie save(Movie movie);
    Boolean updateById(Long id, MovieDto movie) throws NotCreatedException;
    void deleteById(Long movieId);
    Boolean updateAvailabilityById(Long id, Boolean availability) throws NotCreatedException;
}
