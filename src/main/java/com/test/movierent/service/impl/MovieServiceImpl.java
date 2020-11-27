package com.test.movierent.service.impl;

import com.test.movierent.dao.LogMovieDao;
import com.test.movierent.dao.MovieDao;
import com.test.movierent.exception.NotCreatedException;
import com.test.movierent.model.LogMovie;
import com.test.movierent.model.Movie;
import com.test.movierent.model.dto.MovieDto;
import com.test.movierent.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "movieService")
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private LogMovieDao logMovieDao;

    @Override
    public Movie findByMovieId(Long movieId) {
        return movieDao.findById(movieId).orElse(null);
    }

    @Override
    public Movie findByNameMovie(String name) {
        return movieDao.findByTittle(name);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return movieDao.findAll(pageable);
    }

    @Override
    public Page<Movie> findAllByAvailability(Boolean availability, Pageable pageable) {
        return movieDao.findAllByAvailability(availability, pageable);
    }

    @Override
    public Movie save(Movie movie) {
        return movieDao.save(movie);
    }

    @Override
    public Boolean updateById(Long id, MovieDto movie) {
        Movie exist = movieDao.findById(id).orElse(null);
        if (exist == null){
            throw new NotCreatedException("Movie with id: " + id + " not exist");
        }
        /* Save log of data movie update */
        saveLogMovie(exist);

        BeanUtils.copyProperties(movie, exist);
        try {
            movieDao.save(exist);
        }catch (Exception e){
            logger.error("Error to update movie {} ", exist.getTittle());
            return false;
        }
        return true;
    }

    /**
     * @param exist is the movie after update in the database
     **/
    private void saveLogMovie(Movie exist) {
        LogMovie logMovie = new LogMovie();
        logMovie.setMovie(exist);
        logMovie.setTittle(exist.getTittle());
        logMovie.setRentalPrice(exist.getRentalPrice());
        logMovie.setSalePrice(exist.getSalePrice());
        logMovieDao.save(logMovie);
    }

    @Override
    public void deleteById(Long movieId) {
        movieDao.deleteById(movieId);
    }

    // Method for update just column availability of movie
    @Override
    public Boolean updateAvailabilityById(Long id, Boolean availability) {
        Movie exist = movieDao.findById(id).orElse(null);
        if (exist ==null){
            throw new NotCreatedException("Movie with id: " + id + " not exist");
        }
        exist.setAvailability(availability);
        return true;
    }

}
