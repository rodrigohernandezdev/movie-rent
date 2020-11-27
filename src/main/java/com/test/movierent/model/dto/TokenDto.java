package com.test.movierent.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenDto {
    @JsonProperty("access_token")
    private String token;

    public TokenDto(String token){
        this.token = token;
    }
}
