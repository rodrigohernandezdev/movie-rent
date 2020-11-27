package com.test.movierent.bean;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeansTests {
    @Autowired
    private ApplicationContext context;
    Logger logger = LoggerFactory.getLogger(BeansTests.class);

    @Test
    void getServices(){
        assertNotNull(context.getBean("userService"));
        assertNotNull(context.getBean("movieService"));
        assertNotNull(context.getBean("roleService"));
        assertNotNull(context.getBean("userMovieService"));
    }

    @Test
    void getRepositories(){
        assertNotNull(context.getBean("logMovieDao"));
        assertNotNull(context.getBean("movieDao"));
        assertNotNull(context.getBean("movieLikeDao"));
        assertNotNull(context.getBean("movieRentBuyDao"));
        assertNotNull(context.getBean("roleDao"));
        assertNotNull(context.getBean("userDao"));
        assertNotNull(context.getBean("tokenDao"));
    }

}
