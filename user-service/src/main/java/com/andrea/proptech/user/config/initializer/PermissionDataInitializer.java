package com.andrea.proptech.user.config.initializer;

import com.andrea.proptech.user.permission.PermissionName;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Component
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PermissionDataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        log.info("Synchronization of permissions defined in the enum with the database...");

        Set<String> existingPermissions = permissionRepository.findAll().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        List<Permission> permissionsToCreate = Arrays.stream(PermissionName.values())
                .filter(name -> !existingPermissions.contains(name.toString()))
                .map(Permission::new)
                .collect(Collectors.toList());

        if (!permissionsToCreate.isEmpty()) {
            permissionRepository.saveAll(permissionsToCreate);
            log.info("Added {} new permissions to the database.", permissionsToCreate.size());
        }
    }
}
