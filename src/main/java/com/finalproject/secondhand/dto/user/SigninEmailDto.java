package com.finalproject.secondhand.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigninEmailDto {

    @NotBlank(message = "Email harus diisi!")
    @Email(message = "Email tidak valid")
    private String email;
    @NotBlank(message = "Password harus diisi!")
    private String password;
}
