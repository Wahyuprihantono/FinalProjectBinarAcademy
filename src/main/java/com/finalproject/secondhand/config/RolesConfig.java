package com.finalproject.secondhand.config;

import com.finalproject.secondhand.entity.Roles;
import com.finalproject.secondhand.enums.ERole;
import com.finalproject.secondhand.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolesConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesConfig.class);

    RolesConfig (RoleRepository roleRepository) {
        for(ERole role : ERole.values()) {
            try {
                Roles roles = roleRepository.findByRole(role)
                        .orElseThrow(() -> new RuntimeException("Roles not found"));
                LOGGER.info("Role {} has been found!", roles.getRole());
            } catch(RuntimeException rte) {
                LOGGER.info("Role {} is not found, inserting to DB . . .", role.name());
                Roles roles = new Roles();
                roles.setRole(role);
                roleRepository.save(roles);
            }
        }
    }

}
