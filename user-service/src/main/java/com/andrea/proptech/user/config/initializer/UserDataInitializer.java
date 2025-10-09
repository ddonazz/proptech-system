package com.andrea.proptech.user.config.initializer;

import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.data.RoleRepository;
import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.data.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Log4j2
@Component
@AllArgsConstructor
@Order(3)
public class UserDataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking for admin user...");

        String adminUsername = "admin";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            log.info("Admin user not found, creating one...");

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: ROLE_ADMIN is not found."));

            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode("password123"));
            adminUser.setRoles(Set.of(adminRole));

            userRepository.save(adminUser);
            log.info("Admin user created successfully.");
        }
    }
}
