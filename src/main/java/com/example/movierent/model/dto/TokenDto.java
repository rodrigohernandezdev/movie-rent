package com.example.movierent.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    @JsonProperty("access_token")
    private String token;
    public TokenDto(){
    }
    public TokenDto(String token){
        this.token = token;
    }
}
