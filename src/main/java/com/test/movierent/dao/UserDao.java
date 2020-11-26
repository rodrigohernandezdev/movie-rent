package com.test.movierent.dao;

import com.test.movierent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
