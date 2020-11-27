package com.test.movierent.dao;

import com.test.movierent.model.LogMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "logMovieDao")
public interface LogMovieDao extends JpaRepository<LogMovie, Long> {
}
