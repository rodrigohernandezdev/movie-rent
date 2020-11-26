package com.test.movierent.service;

import com.test.movierent.exception.NotExistException;

public interface LikeService {
    void like(Long movieId) throws NotExistException;
}
