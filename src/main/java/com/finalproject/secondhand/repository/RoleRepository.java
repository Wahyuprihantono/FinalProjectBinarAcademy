package com.finalproject.secondhand.repository;

import com.finalproject.secondhand.entity.Roles;
import com.finalproject.secondhand.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRole(ERole role);
}
