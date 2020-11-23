package com.example.movierent.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String username;
    private String password;
    private int age;
}
