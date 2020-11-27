package com.test.movierent.dto;


import com.test.movierent.model.Role;
import com.test.movierent.model.User;
import com.test.movierent.model.VerificationToken;
import com.test.movierent.model.dto.MovieDto;
import com.test.movierent.model.dto.OnVerificationUserEvent;
import com.test.movierent.model.dto.TokenDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DtoTests {

    @Test()
    void verifyExceptions() {
        MovieDto movie = new MovieDto();
        movie.setTittle("test");
        assertNotNull(movie.toString());
    }

    @Test()
    void verifyToken() {
        TokenDto tokenDto = new TokenDto("test");
        assertNotNull(tokenDto.toString());
    }
    @Test()
    void onVerification(){
        User user = new User();
        user.addRole(new Role());
        assertThat(new OnVerificationUserEvent(user, new Locale("en"), "localhost", 1)).isNotNull();
    }

    @Test
    void verificationToken(){
        VerificationToken myToken = new VerificationToken("123", new User(), 1);
        assertNotNull(myToken.getExpiryDate());
    }

}
