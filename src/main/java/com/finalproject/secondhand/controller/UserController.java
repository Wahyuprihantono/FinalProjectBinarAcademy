package com.finalproject.secondhand.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.secondhand.dto.user.ChangePasswordDto;
import com.finalproject.secondhand.dto.user.UserUpdateDto;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.dto.response.UserDetailResponse;
import com.finalproject.secondhand.service.image.CloudinaryStorageService;
import com.finalproject.secondhand.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Tag(name = "User", description = "API for processing CRUD Users")
@RequestMapping("/api/user/")
@SecurityRequirement(name = "Authorization")
@CrossOrigin(origins = {"http://localhost:3000", "*"}, allowedHeaders = "*")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryStorageService cloudinaryStorageService;

    @Operation(summary = "current user details")
    @GetMapping("/current-detail")
    public ResponseEntity<UserDetailResponse> currentUserDetails(Authentication authentication) {
        String username = authentication.getName();
        return new ResponseEntity<>(userService.curentUserDetail(username), HttpStatus.OK);
    }

    @Operation(summary = "Edit user profil")
    @PutMapping(value = "update",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                        MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Users> updateUsers(
            @RequestPart (required = false) String updateJson,
            @RequestPart (name = "imageProfil", required = false) MultipartFile imageProfil, Authentication authentication) {
        String username = authentication.getName();
        Users users = new Users();
        UserUpdateDto update = new UserUpdateDto();
        try {
            ObjectMapper om = new ObjectMapper();
            update = om.readValue(updateJson, UserUpdateDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        users.setFullname(update.getFullname());
        users.setCity(update.getCity());
        users.setAddress(update.getAddress());
        users.setPhone(update.getPhone());
        LOGGER.info(update.getFullname());
        if (imageProfil == null) {
            LOGGER.info("Skip upload...");
        } else {
                Map<?, ?> uploadImage = (Map<?, ?>) cloudinaryStorageService.upload(imageProfil).getData();
                users.setImageProfil(uploadImage.get("url").toString());
        }
        return new ResponseEntity<>(userService.update(users, username), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Change Password")
    @PutMapping( value = "change-password",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> changePassword(
            @RequestPart String changeJson, Authentication valid) {
        String username = valid.getName();
        ChangePasswordDto change = new ChangePasswordDto();
        try {
            ObjectMapper om = new ObjectMapper();
            change = om.readValue(changeJson, ChangePasswordDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        Users users = userService.findByUsername(username);
        if (change.getOldPassword() != null) {
            if (passwordEncoder.matches(change.getOldPassword(),users.getPassword())) {
                if (change.getNewPassword().equals(change.getConfirmPassword())) {
                    users.setPassword(passwordEncoder.encode(change.getNewPassword()));
                    return new ResponseEntity<>(userService.changePassword(users, username), HttpStatus.ACCEPTED);
                }else {
                    return new ResponseEntity<>("New password and confirm password not same", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Old password incorrect", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Old password null", HttpStatus.NOT_FOUND);
        }
    }
}