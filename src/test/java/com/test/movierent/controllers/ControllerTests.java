package com.test.movierent.controllers;

import com.test.movierent.controller.AuthController;
import com.test.movierent.exception.ParameterException;
import com.test.movierent.exception.TokenException;
import com.test.movierent.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(ControllerTests.class);
    @Autowired
    private AuthController authController;

    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    private final Model model = mock(Model.class);

    @Test
    void contextLoads() throws Exception {
        assertThat(authController).isNotNull();
    }


    @Test
    void testLogin(){
        UserDto loginUser = new UserDto();
        loginUser.setEmail("test@test.com");
        loginUser.setPassword("1");
        assertThrows(Exception.class, () -> {
            authController.login(loginUser);
        });

    }

    @Test
    void testSignup(){
        UserDto loginUser = new UserDto();
        loginUser.setEmail("test@test.com");
        loginUser.setPassword("12");
        assertThrows(ParameterException.class, () -> {
            authController.signup(loginUser, httpServletRequest);
        });

    }
    @Test
    void testConfirm(){
        assertThrows(TokenException.class, () -> {
            authController.confirmRegistration("123");
        });

    }

    @Test
    void testRequest(){
        UserDto loginUser = new UserDto();
        loginUser.setEmail("test@test.com");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("http://localhost:8080");
        assertThrows(Exception.class, () -> {
            authController.recovery(loginUser, null);
        });

    }

    @Test
    void testGetRecovery(){
        assertThat(authController.recoveryToken("124", model)).isNotEmpty();
    }

    @Test
    void testGetRecoveryNotToken(){
        assertThrows(ParameterException.class, () -> {
            authController.recoveryToken("", model);
        });
    }

    @Test
    void testPostRecovery(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("token", "123");
        request.setParameter("password", "12345678");
        request.setParameter("confirmPassword", "12345678");

        assertThrows(TokenException.class, () -> {
            authController.recoveryToken( request);
        });

    }


}
