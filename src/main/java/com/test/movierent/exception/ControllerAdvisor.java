package com.test.movierent.exception;

import com.test.movierent.config.CorsFilter;
import com.test.movierent.config.MessageProvider;
import com.test.movierent.model.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Util Class for Exceptions handler and return custom responses
 * All response return object ErrorDto with { timestamp, status, error, message} structure
 **/

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Autowired
    MessageProvider messageProvider;

    // Handler for spring BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(
            BadCredentialsException ex) {

        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError(ex.getMessage());
        error.setMessage(messageProvider.getBadCredentials());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Handler for custom UserAlreadyExistException
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExistException(
            UserAlreadyExistException ex) {

        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(ex.getMessage());
        error.setMessage(messageProvider.getUserExist());
        logger.error("An error has occurred to register a new user ", ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handler for general Exception
    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(
            Exception ex) {

        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError(ex.getMessage());
        error.setMessage(messageProvider.getErrorGeneral());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    // Handler for custom ParameterException
    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<ErrorDto> handleMissingParameterException(
            ParameterException ex) {
        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(ex.getMessage());
        error.setMessage(messageProvider.getParameterMissing());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handler for custom TokenException
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorDto> handleTokenException(
            TokenException ex) {

        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(ex.getMessage());
        String message;
        if (ex.getType() == 2){
            message = messageProvider.getExpiredToken();
        }else if (ex.getType() == 3){
            message = messageProvider.getTokenNoUser();
        }else{
            message = messageProvider.getInvalidToken();
        }
        error.setMessage(message);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotCreatedException.class)
    public ResponseEntity<ErrorDto> handleNotCreatedException(
            NotCreatedException ex) {
        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError(ex.getMessage());
        error.setMessage(messageProvider.getNotCreated());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // Handler for spring AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(
            AccessDeniedException ex) {

        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setError(ex.getMessage());
        error.setMessage(messageProvider.getForbidden());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}
