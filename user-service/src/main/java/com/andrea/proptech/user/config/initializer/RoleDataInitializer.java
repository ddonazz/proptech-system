package com.andrea.proptech.user.config.initializer;

import com.andrea.proptech.core.security.permission.PermissionAuthority;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.data.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Component
@AllArgsConstructor
@DependsOn("permissionDataInitializer")
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        log.info("Creating default roles...");

        createRoleIfNotFound("ROLE_ADMIN", "Amministratore", new HashSet<>(permissionRepository.findAll()));

        Set<Permission> userPermissions = Stream.builder()
                .add(PermissionAuthority.USER_READ)
                .add(PermissionAuthority.PERMISSION_READ)
                .add(PermissionAuthority.ROLE_READ)
                .build()
                .map(p -> permissionRepository.findByAuthority(p.toString()).orElseThrow())
                .collect(Collectors.toSet());
        createRoleIfNotFound("ROLE_USER", "Utente", userPermissions);

        log.info("Default roles created.");
    }

    private void createRoleIfNotFound(String roleName, String description, Set<Permission> permissions) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            role.setDescription(description);
            role.setPermissions(permissions);
            roleRepository.save(role);
            log.info("Role {} created with {} permissions.", roleName, permissions.size());
        }
    }

}
