package com.finalproject.secondhand.entity;

import com.finalproject.secondhand.dto.user.SignupDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "image_profil")
    private String imageProfil;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Roles> roles;

    public Users(SignupDto signup) {
        this.fullname= signup.getFullname();
        this.username = signup.getUsername();
        this.email = signup.getEmail();
        this.password = signup.getPassword();
    }

    public Users() {

    }

}

