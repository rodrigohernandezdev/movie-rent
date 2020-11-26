package com.test.movierent.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:message.properties")
@Getter
public class MessageProvider {

    @Value("${error.bad-credentials}")
    private String badCredentials;

    @Value("${error.unauthorized}")
    private String unauthorized;

    @Value("${error.user-exist}")
    private String userExist;

    @Value("${error.general}")
    private String errorGeneral;

    @Value("${error.invalid-token}")
    private String invalidToken;

    @Value("${error.expired-token}")
    private String expiredToken;

    @Value("${error.token-not-user}")
    private String tokenNoUser;

    @Value("${error.parameter-missing}")
    private String parameterMissing;

    @Value("${error.not-created}")
    private String notCreated;

    @Value("${error.forbidden}")
    private String forbidden;


}
