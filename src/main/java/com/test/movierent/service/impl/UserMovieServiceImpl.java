package com.test.movierent.service.impl;

import com.test.movierent.dao.MovieUserLikedDao;
import com.test.movierent.exception.NotExistException;
import com.test.movierent.model.Movie;
import com.test.movierent.model.MovieUserLiked;
import com.test.movierent.model.User;
import com.test.movierent.service.UserMovieService;
import com.test.movierent.service.MovieService;
import com.test.movierent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserMovieServiceImpl implements UserMovieService {
    @Autowired
    private MovieUserLikedDao movieLikeDao;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private MovieService movieService;

    /**
     * Method for like a movie with authentication of spring security context
     **/
    @Override
    public void like(Long movieId) throws NotExistException {
        User user = userService.getUserFromAuth();
        if (user == null){
            throw new NotExistException("Can not obtain the user for like movie");
        }
        Movie movie = movieService.findByMovieId(movieId);
        if (movie == null){
            throw new NotExistException("Can not obtain the movie: "+movieId);
        }
        // Validate if exist a like of user and movie
        if (movieLikeDao.countByMovieIdAndUserId(movieId, user.getId())<=0){
            MovieUserLiked like = new MovieUserLiked();
            like.setMovie(movie);
            like.setUser(user);
            movieLikeDao.save(like);
            // Change number of likes from total of movieLikeUser table
            movie.setLikes(Integer.valueOf(movieLikeDao.countByMovieId(movie.getId()).toString()));
            movieService.save(movie);
        }
    }

    @Override
    public Movie rent(Long movieId) {
        User user = userService.getUserFromAuth();
        if (user == null){
            throw new NotExistException("Can not obtain the user for like movie");
        }

        return null;
    }
}
