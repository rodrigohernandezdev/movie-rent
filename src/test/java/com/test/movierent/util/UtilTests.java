package com.test.movierent.util;

import com.test.movierent.config.CorsFilter;
import com.test.movierent.config.JwtAuthEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilTests {

    @Autowired
    CorsFilter corsFilter;
    @Autowired
    JwtAuthEntryPoint jwtAuthEntryPoint;

    @Test
    void contextLoads() {
        assertNotNull(corsFilter);
    }
    @Test
    void corsDoFilter() throws IOException, ServletException {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        corsFilter.doFilter(httpServletRequest, httpServletResponse,
                filterChain);
        assertNotNull(httpServletResponse);
    }

    @Test
    void commerceTest() throws IOException {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        jwtAuthEntryPoint.commence(httpServletRequest, httpServletResponse, null);
        assertNotNull(httpServletResponse.getContentType());
    }
}
