package com.test.movierent.model.dto;

import com.test.movierent.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnVerificationUserEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;
    private Integer type;

    public OnVerificationUserEvent(User user, Locale locale, String appUrl, Integer type) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.type = type;
    }
}
