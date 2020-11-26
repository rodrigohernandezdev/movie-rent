package com.example.movierent.service;


import com.example.movierent.exception.UserAlreadyExistException;
import com.example.movierent.model.User;
import com.example.movierent.model.VerificationToken;
import com.example.movierent.model.dto.UserDto;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void delete(long id);
    User findOne(String email);
    User findById(Long id);

    User register(UserDto userDto) throws UserAlreadyExistException;
    void saveRegisteredUser(User user, VerificationToken verificationToken);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String VerificationToken);

    void createRecoveryToken(User user, String token);
    VerificationToken getRecoveryToken(String VerificationToken);
    void saveRecoveryToken(VerificationToken verificationToken, String newPassword);

}
