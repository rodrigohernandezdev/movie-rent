package com.example.movierent.service;


import com.example.movierent.model.User;
import com.example.movierent.model.dto.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);
    User findById(Long id);
}
