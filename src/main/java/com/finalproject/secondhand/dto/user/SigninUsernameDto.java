package com.finalproject.secondhand.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigninUsernameDto {

    @NotBlank(message = "Username harus diisi!")
    private String username;
    @NotBlank(message = "Password harus diisi!")
    private String password;
}
