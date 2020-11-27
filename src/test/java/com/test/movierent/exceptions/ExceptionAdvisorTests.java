package com.test.movierent.exceptions;

import com.test.movierent.exception.ControllerAdvisor;
import com.test.movierent.exception.NotCreatedException;
import com.test.movierent.exception.NotExistException;
import com.test.movierent.exception.ParameterException;
import com.test.movierent.exception.TokenException;
import com.test.movierent.exception.UserAlreadyExistException;
import com.test.movierent.exception.UserMovieException;
import com.test.movierent.model.dto.ErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExceptionAdvisorTests {
    @Autowired
    private ControllerAdvisor controllerAdvisor;

    @Test
    void handleBadCredentialsExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleBadCredentialsException(new BadCredentialsException("Bad Credentials"));
        assertNotNull(res);
    }

    @Test
    void handleUserAlreadyExistExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleUserAlreadyExistException(new UserAlreadyExistException("Already exist"));
        assertNotNull(res);
    }

    @Test
    void handleMissingParameterExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleMissingParameterException(new ParameterException("Error in the first Param"));
        assertNotNull(res);
    }

    @Test
    void handleTokenExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleTokenException(new TokenException("Token is invalid", 1));
        assertNotNull(res.getBody());
    }

    @Test
    void handleTokenException2Test(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleTokenException(new TokenException("Token is invalid", 2));
        assertNotNull(res.getStatusCode());
    }

    @Test
    void handleTokenException3Test(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleTokenException(new TokenException("Token is invalid", 3));
        assertNotNull(res);
    }

    @Test
    void handleNotCreatedExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleNotCreatedException(new NotCreatedException("Can not be created"));
        assertNotNull(res);
    }
    @Test
    void handleAccessDeniedExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleAccessDeniedException(new AccessDeniedException("You do not have permission"));
        assertNotNull(res);
    }

    @Test
    void handleNotExistExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleNotExistException(new NotExistException("Not Exist"));
        assertNotNull(res);
    }

    @Test
    void handleUserMovieExceptionTest(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleUserMovieException(new UserMovieException("the movie is not available", 1));
        assertNotNull(res);
    }
    @Test
    void handleUserMovieExceptionTest2(){
        ResponseEntity<ErrorDto> res = controllerAdvisor
                .handleUserMovieException(new UserMovieException("the movie is not available", 2));
        assertNotNull(res.getStatusCode());
    }

}
