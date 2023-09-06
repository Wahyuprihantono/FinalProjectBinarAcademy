package com.finalproject.secondhand.dto.user;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    @NotBlank(message = "Nama Lengkap harus diisi!")
    private String fullname;
    @NotBlank(message = "Username harus diisi!")
    private String username;
    @Email(message = "Email tidak valid!")
    @NotBlank(message = "Email harus diisi!")
    private String email;
    @NotBlank(message = "Password harus diisi!")
    private String password;

}
