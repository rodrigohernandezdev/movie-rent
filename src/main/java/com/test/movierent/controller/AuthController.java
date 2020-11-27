package com.test.movierent.controller;

import com.test.movierent.config.TokenProvider;
import com.test.movierent.exception.NotExistException;
import com.test.movierent.exception.ParameterException;
import com.test.movierent.exception.TokenException;
import com.test.movierent.model.User;
import com.test.movierent.model.VerificationToken;
import com.test.movierent.model.dto.OnVerificationUserEvent;
import com.test.movierent.model.dto.TokenDto;
import com.test.movierent.model.dto.UserDto;
import com.test.movierent.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Controller class for the authentication or registration functionality
 * all methods are permitted without authenticate
 **/
@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Value("${application.password-length}")
    private Integer passwordLength;

    private final String keyResponse = "message";

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto loginUser) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new ResponseEntity<>(new TokenDto(token), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDto userDto, HttpServletRequest request) {

        if (userDto.getPassword().length() < this.passwordLength) {
            throw new ParameterException("Password length can not be less than 8 characters");
        }
        User registered = userService.register(userDto);
        String appUrl = getUrlPath(request);
        eventPublisher.publishEvent(new OnVerificationUserEvent(registered, request.getLocale(), appUrl, 1));
        logger.info("User: {} has been created", registered.getEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/confirm")
    public ResponseEntity<Map<String, String>> confirmRegistration(@RequestParam("token") String token){
        VerificationToken verificationToken = userService.getVerificationToken(token);
        verifyToken(verificationToken);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        verificationToken.setEnabled(false);
        userService.saveRegisteredUser(user, verificationToken);
        Map<String, String> result = new LinkedHashMap<>();
        result.put(keyResponse, "User has been verified");
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }


    @PostMapping("/reset-request")
    public ResponseEntity<?> recovery(@RequestBody UserDto userDto, HttpServletRequest request) {
        User exist = userService.findOne(userDto.getEmail());
        if (exist == null) {
            throw new NotExistException("User is not registered");
        }
        String appUrl = getUrlPath(request);
        eventPublisher.publishEvent(new OnVerificationUserEvent(exist, request.getLocale(), appUrl, 2));
        logger.info("Has send a link to reset password to email");
        Map<String, String> result = new LinkedHashMap<>();
        result.put(keyResponse, String.format("Please check your email at %s for a link to reset your password", exist.getEmail()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Use a thymeleaf template for the recovery functionality
     **/
    @GetMapping("/recovery")
    public String recoveryToken(@RequestParam(value = "recovery_token", required = false) String token,
                                Model model) {

        if (token == null || token.isEmpty()) {
            throw new ParameterException("Parameter {token} is null");
        }

        model.addAttribute("recovery_token", token);
        return "recovery";
    }

    /**
     * Recovery password from thymeleaf template {recovery}
     **/
    @PostMapping("/recovery")
    public ResponseEntity<?> recoveryToken(
            HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            throw new ParameterException("Parameter {token} is null");
        }

        String password = request.getParameter("password");
        if (password == null || password.isEmpty()) {
            throw new ParameterException("Parameter {password} is null");
        }

        String confirmPassword = request.getParameter("confirmPassword");
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new ParameterException("Parameter {confirmPassword} is null");
        }

        // Compare the two password if equals
        if (!password.equals(confirmPassword)) {
            throw new ParameterException("Parameter {password} does not match with {confirmPassword} ");
        }


        if (password.length() < this.passwordLength) {
            throw new ParameterException("Password length can not be less than 8 characters");
        }

        VerificationToken recoveryToken = userService.getRecoveryToken(token);

        verifyToken(recoveryToken);

        recoveryToken.setEnabled(false);

        userService.saveRecoveryToken(recoveryToken, password);

        Map<String, String> result = new LinkedHashMap<>();
        result.put(keyResponse, "The password has been changed successfully");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * @param request for get the complete url
     * */
    private String getUrlPath(HttpServletRequest request) {
        return request.getRequestURL().toString().replace("/signup", "").replace("/reset-request", "");
    }

    // verify if token is enabled or not expired
    private void verifyToken(VerificationToken recoveryToken) {
        String invalid = "Token is not valid";
        if (recoveryToken == null || !recoveryToken.getEnabled()) {
            throw new TokenException(invalid, 1);
        }
        Calendar cal = Calendar.getInstance();
        if ((recoveryToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new TokenException(invalid, 2);
        }
        User user = recoveryToken.getUser();
        if (user == null) {
            throw new TokenException(invalid, 3);
        }
    }

}
