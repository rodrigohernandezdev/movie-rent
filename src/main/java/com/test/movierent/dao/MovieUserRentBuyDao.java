package com.test.movierent.dao;

import com.test.movierent.model.MovieUserRentBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "movieRentBuyDao")
public interface MovieUserRentBuyDao extends JpaRepository<MovieUserRentBuy, Long> {

    Long countByMovieId(Long movieId);

    Long countByUserId(@Param("user_id") Long user_id);

    Long countByMovieIdAndUserId(@Param("movieId") Long movieId, @Param("userId") Long userId);

    List<MovieUserRentBuy> findAllByMovieIdAndUserIdAndType(@Param("movieId") Long movieId, @Param("userId") Long userId,@Param("type") Integer type);

}
