package com.finalproject.secondhand.service.user;

import com.finalproject.secondhand.dto.response.JwtTokenDto;
import com.finalproject.secondhand.dto.user.SigninUsernameDto;
import com.finalproject.secondhand.dto.user.SignupDto;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.dto.response.UserDetailResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    //CREATE
    void save(SignupDto signupDto);

    //READ
    List<Users> findAll();
    Users findUserByEmail(String email);
    UserDetailResponse curentUserDetail(String username);

    //LOGIN
    JwtTokenDto loginEmail(SigninUsernameDto signin);
    JwtTokenDto loginUsername(SigninUsernameDto signin);

    boolean existsUsername(String username);
    boolean existsEmail(String email);

    //UPDATE
    Users update(Users body, String username);
    String changePassword(Users body, String username);

    //AUTH
    Users findByUsername(String username);


}
