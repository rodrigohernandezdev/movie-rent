package com.test.movierent.services;

import com.test.movierent.exception.UserAlreadyExistException;
import com.test.movierent.model.User;
import com.test.movierent.model.dto.UserDto;
import com.test.movierent.service.MovieService;
import com.test.movierent.service.RoleService;
import com.test.movierent.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ServicesTests {

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MovieService movieService;

    @Test()
    void loadByUsernameTest(){
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("test");
        });
    }

    @Test()
    void loadUserByUsernameTest(){
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@test.com");
        assertNotNull(userDetails);
    }

    @Test()
    void findAllTest(){
        assertNotNull(userService.findAll());
    }

    @Test()
    void emailExistTest(){
        assertNotNull(userService.emailExist("test"));
    }

    @Test
    void roleFindTest(){
        assertNotNull(roleService.findByName("USER"));
    }

    @Test
    void roleFindAllTest(){
        assertNotNull(roleService.findAll());
    }

    @Test
    void roleFindOneTest(){
        assertNotNull(roleService.findById(1L));
    }

    @Test
    void findByMovieIdTest(){
        movieService.findByMovieId(1L);
        assertNotNull(movieService);
    }
    @Test
    void findByMovieName(){
        assertNull(movieService.findByNameMovie("1"));
    }
    @Test
    void findAllMovie(){
        Pageable pageable = PageRequest.of(1, 1);
        assertNotNull(movieService.findAll(pageable));
    }
}
