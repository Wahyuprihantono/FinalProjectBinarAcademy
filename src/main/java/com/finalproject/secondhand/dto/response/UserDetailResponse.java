package com.finalproject.secondhand.dto.response;

import com.finalproject.secondhand.entity.Users;
import lombok.Data;

@Data
public class UserDetailResponse {

    private String username;
    private String email;
    private String fullname;
    private String city;
    private String address;
    private String phone;
    private String imageProfile;

    public UserDetailResponse(Users users){
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.fullname = users.getFullname();
        this.city = users.getCity();
        this.address = users.getAddress();
        this.phone = users.getPhone();
        this.imageProfile = users.getImageProfil();
    }
}
