package com.example.movierent.dao;

import com.example.movierent.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenDao extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByTokenAndType(String token, Integer type);
}
