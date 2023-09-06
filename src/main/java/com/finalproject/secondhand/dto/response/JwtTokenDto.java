package com.finalproject.secondhand.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JwtTokenDto {
    private String accessToken;
    private String type = "Bearer";
    private Integer userId;
    private String username;
    private String email;
    private List<String> roles;

    public JwtTokenDto(String accessToken, Integer userId, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtTokenDto() {

    }
}
