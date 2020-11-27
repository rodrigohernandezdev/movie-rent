package com.test.movierent.dao;

import com.test.movierent.model.MovieUserLiked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "movieLikeDao")
public interface MovieUserLikedDao extends JpaRepository<MovieUserLiked, Long> {

    Long countByMovieId(Long movieId);

    Long countById(Long id);

    Long countByUserId(@Param("user_id") Long user_id);

    Long countByMovieIdAndUserId(@Param("movieId") Long movieId, @Param("userId") Long userId);

}
