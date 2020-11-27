package com.test.movierent.service.impl;

import com.test.movierent.dao.MovieUserLikedDao;
import com.test.movierent.dao.MovieUserRentBuyDao;
import com.test.movierent.exception.NotExistException;
import com.test.movierent.exception.UserMovieException;
import com.test.movierent.model.Movie;
import com.test.movierent.model.MovieUserLiked;
import com.test.movierent.model.MovieUserRentBuy;
import com.test.movierent.model.User;
import com.test.movierent.service.MovieService;
import com.test.movierent.service.UserMovieService;
import com.test.movierent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service(value = "userMovieService")
public class UserMovieServiceImpl implements UserMovieService {
    @Autowired
    private MovieUserLikedDao movieLikeDao;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieUserRentBuyDao movieRentBuyDao;

    private static final String canNotObtain = "Can not obtain the movie: ";
    /**
     * Method for like a movie with authentication of spring security context
     **/
    @Override
    public void like(Long movieId) {
        User user = userService.getUserFromAuth();
        if (user == null) {
            throw new NotExistException("Can not obtain the user for like movie");
        }
        Movie movie = movieService.findByMovieId(movieId);
        if (movie == null) {
            throw new NotExistException(canNotObtain + movieId);
        }
        // Validate if exist a like of user and movie
        if (movieLikeDao.countByMovieIdAndUserId(movieId, user.getId()) <= 0) {
            MovieUserLiked like = new MovieUserLiked();
            like.setMovie(movie);
            like.setUser(user);
            movieLikeDao.save(like);
            // Change number of likes from total of movieLikeUser table
            movie.setLikes(Integer.valueOf(movieLikeDao.countByMovieId(movie.getId()).toString()));
            movieService.save(movie);
        }
    }

    /**
     * Functionality for rent any quantity of movies by a user authenticated
     **/
    @Override
    public MovieUserRentBuy rent(Long movieId, Integer quantity) {
        User user = userService.getUserFromAuth();
        if (user == null) {
            throw new NotExistException("Can not obtain the user for rent movie");
        }
        Movie movie = movieService.findByMovieId(movieId);
        if (movie == null) {
            throw new NotExistException(canNotObtain + movieId + " for rent");
        }
        if (movie.getStock() < quantity) {
            throw new UserMovieException("The stock of movie " + movie.getTittle() + " is less than the quantity to rent", 1);
        }

        MovieUserRentBuy movieRent = new MovieUserRentBuy();
        movieRent.setMovie(movie);
        movieRent.setQuantity(quantity);
        movieRent.setRentDate(LocalDateTime.now());
        movieRent.setUser(user);
        BigDecimal total;
        try {
            total = movie.getRentalPrice().multiply(new BigDecimal(quantity));
        } catch (Exception e) {
            throw new UserMovieException("An error has occurred: " + e, 1);
        }
        movieRent.setTotalPay(total);
        movieRent.setType(1);

        return saveRentBuy(movieRent, movie, quantity);
    }

    /**
     * Functionality for buy any quantity of movies by a user authenticated
     **/
    @Override
    public MovieUserRentBuy buy(Long movieId, Integer quantity) {
        User user = userService.getUserFromAuth();
        if (user == null) {
            throw new NotExistException("Can not obtain the user for buy movie");
        }
        Movie movie = movieService.findByMovieId(movieId);
        if (movie == null) {
            throw new NotExistException(canNotObtain+ + movieId + " for buy");
        }
        if (movie.getStock() < quantity) {
            throw new UserMovieException("The stock of movie: " + movie.getTittle() + " is less than the quantity to buy", 2);
        }

        MovieUserRentBuy movieBuy = new MovieUserRentBuy();
        movieBuy.setMovie(movie);
        movieBuy.setQuantity(quantity);
        movieBuy.setRentDate(LocalDateTime.now());
        movieBuy.setUser(user);
        BigDecimal total;
        try {
            total = movie.getSalePrice().multiply(new BigDecimal(quantity));
        } catch (Exception e) {
            throw new UserMovieException("An error has occurred: " + e.getMessage(), 2);
        }
        movieBuy.setTotalPay(total);
        movieBuy.setType(2);

        return saveRentBuy(movieBuy, movie, quantity);
    }

    @Override
    public MovieUserRentBuy findByUserAndMovieId(Long movieId) {
        User user = userService.getUserFromAuth();
        if (user == null) {
            throw new NotExistException("Can not obtain the user for return rent movie");
        }
        if (movieRentBuyDao.countByMovieIdAndUserId(movieId, user.getId()) > 0) {
            return movieRentBuyDao.findAllByMovieIdAndUserIdAndType(movieId, user.getId(), 1).get(0);
        }
        throw new NotExistException("This movie: " + movieId + " not be rent by user: " + user.getFirstName());
    }

    /**
     * Functionality for return a movie
     **/
    @Override
    public MovieUserRentBuy returnMovie(MovieUserRentBuy returnMovie) {
        returnMovie.setRentDate(LocalDateTime.now());
        MovieUserRentBuy saved = movieRentBuyDao.save(returnMovie);
        // If the movie not was returned not increment stock
        if (saved.getMovie() == null || saved.getMovie().getId() == null){
            throw new UserMovieException("An error has occurred to return the movie", 2);
        }
        Movie movie = returnMovie.getMovie();
        movie.setStock(movie.getStock() + 1);
        movieService.save(movie);
        return returnMovie;
    }

    private MovieUserRentBuy saveRentBuy(MovieUserRentBuy movieRentBuy, Movie movie, Integer quantity) {
        MovieUserRentBuy saved = movieRentBuyDao.save(movieRentBuy);
        // Subtract quantity to rent or buy from movie stock
        if (saved.getId() != null) {
            movie.setStock(movie.getStock() - quantity);
            movieService.save(movie);
        } else {
            throw new UserMovieException("An error has occurred to rent the movie: " + movie.getTittle(), movieRentBuy.getType());
        }
        return saved;
    }
}
