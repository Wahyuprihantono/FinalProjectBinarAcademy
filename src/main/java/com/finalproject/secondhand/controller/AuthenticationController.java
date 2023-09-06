package com.finalproject.secondhand.controller;

import com.finalproject.secondhand.config.security.JwtUtil;
import com.finalproject.secondhand.dto.response.JwtTokenDto;
import com.finalproject.secondhand.dto.user.SigninEmailDto;
import com.finalproject.secondhand.dto.user.SigninUsernameDto;
import com.finalproject.secondhand.dto.user.SignupDto;
import com.finalproject.secondhand.entity.UserDetailsImpl;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Authentication", description = "API for Login and Register")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "*"}, allowedHeaders = "*")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "NOT FOR PUBLIC")
    @GetMapping("/list-user")
    public List<Users> listAll() {
        return userService.findAll();
    }

    @Operation(summary = "Registers a new user")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid
            @Schema(example = "{" +
                    "\"fullname\":\"Ellda Artha Airlannga\"," +
                    "\"username\":\"ellda\"," +
                    "\"email\":\"ellda@gmail.com\"," +
                    "\"password\":\"ellda123\"" +
                    "}")
            @RequestBody SignupDto signup) {
        HashMap<String, String> response = new HashMap();
        if (userService.existsUsername(signup.getUsername())) {
            response.put("error", "Username already used");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (userService.existsEmail(signup.getEmail())) {
            response.put("error", "Email already used");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(signup.toString());
        signup.setPassword(passwordEncoder.encode(signup.getPassword()));
        signup.setFullname(signup.getFullname());
        LOGGER.info(signup.getFullname());
        userService.save(signup);
        return new ResponseEntity<>("User Registered", HttpStatus.CREATED);
    }

    @Operation(summary = "Login user with username or email")
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @Schema(example = "{" +
                    "\"email\":\"ellda\"," +
                    "\"password\":\"ellda123\"" +
                    "}")
            @RequestBody SigninUsernameDto signin) {
        LOGGER.info("logging in");
        HashMap<String, String> response = new HashMap();
        if (userService.existsUsername(signin.getUsername())) {
            if (!passwordEncoder.matches(signin.getPassword(), userService.findByUsername(signin.getUsername()).getPassword())) {
                response.put("error", "Password incorrect");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userService.loginUsername(signin), HttpStatus.OK);
        } else if (userService.existsEmail(signin.getUsername())){
            Users users = userService.findUserByEmail(signin.getUsername());
            if (!passwordEncoder.matches(signin.getPassword(), userService.findByUsername(users.getUsername()).getPassword())) {
                response.put("error", "Password incorrect");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userService.loginEmail(signin), HttpStatus.OK);
        } else {
            response.put("error", "user not found");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Login user with email")
    @PostMapping("/signin/email")
    public ResponseEntity<?> signinEmail(
            @Schema(example = "{" +
                    "\"email\":\"ellda@gmail.com\"," +
                    "\"password\":\"ellda123\"" +
                    "}")
            @RequestBody SigninEmailDto signin) {
        LOGGER.info("logging in");
        HashMap<String, String> response = new HashMap();
        if (!userService.existsEmail(signin.getEmail())){
            response.put("error", "User not found");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Users users = userService.findUserByEmail(signin.getEmail());
        if (!passwordEncoder.matches(signin.getPassword(), userService.findByUsername(users.getUsername()).getPassword())) {
            response.put("error", "Password incorrect");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(),
                signin.getPassword()));
        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>("Email or password incorrect", HttpStatus.OK);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateAccessToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        LOGGER.info("User " + userDetails.getUsername() + " logged in.");
        LOGGER.info(token);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtTokenDto(token,
                userDetails.getUserId(), userDetails.getUsername(), userDetails.getEmail(),
                roles));
    }
}