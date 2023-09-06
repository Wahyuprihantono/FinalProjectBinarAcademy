package com.finalproject.secondhand.entity;

import com.finalproject.secondhand.enums.ERole;
import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "roles")
@EqualsAndHashCode(callSuper = true)
public class Roles extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Roles() {
        // This method is empty because it is needed when object is ready
    }
    public Roles(Integer roleId, ERole role){
        this.roleId = roleId;
        this.role = role;
    }

}
