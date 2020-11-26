package com.test.movierent.dao;


import com.test.movierent.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDao extends JpaRepository<Movie, Long> {

    Page<Movie> findAll(@NonNull Pageable pageable);

    Page<Movie> findAllByAvailability(Boolean availability, Pageable pageable);

    Movie findByTittle(String tittle);

}
