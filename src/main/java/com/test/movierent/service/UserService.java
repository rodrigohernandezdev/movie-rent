package com.test.movierent.service;


import com.test.movierent.exception.UserAlreadyExistException;
import com.test.movierent.model.User;
import com.test.movierent.model.VerificationToken;
import com.test.movierent.model.dto.UserDto;

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

    User getUserFromAuth();

}
