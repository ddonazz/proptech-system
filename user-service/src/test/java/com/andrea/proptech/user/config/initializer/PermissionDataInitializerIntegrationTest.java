package com.andrea.proptech.user.config.initializer;

import com.andrea.proptech.user.permission.PermissionName;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PermissionDataInitializerIntegrationTest {

    @Autowired
    private PermissionDataInitializer permissionDataInitializer;

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setUp() {
        permissionRepository.deleteAll();
    }

    @Test
    void run_whenDatabaseIsEmpty_shouldSaveAllPermissionsFromEnum() {
        long initialCount = permissionRepository.count();
        assertEquals(0, initialCount, "The database should be empty before the test.");

        permissionDataInitializer.run();

        long finalCount = permissionRepository.count();
        assertEquals(PermissionName.values().length, finalCount, "All permissions from the enum should have been saved.");
    }

    @Test
    void run_whenSomePermissionsExist_shouldOnlySaveMissingPermissions() {
        permissionRepository.save(new Permission(PermissionName.USER_READ));
        long initialCount = permissionRepository.count();
        assertEquals(1, initialCount, "There should be only one permission before the test.");

        permissionDataInitializer.run();

        long finalCount = permissionRepository.count();
        assertEquals(PermissionName.values().length, finalCount, "The final number of permissions must match the enum's total.");
    }

    @Test
    void run_whenAllPermissionsExist_shouldNotSaveAnything() {
        Arrays.stream(PermissionName.values())
                .map(Permission::new)
                .forEach(permissionRepository::save);

        long initialCount = permissionRepository.count();
        assertEquals(PermissionName.values().length, initialCount, "All permissions should already exist in the DB.");

        permissionDataInitializer.run();

        long finalCount = permissionRepository.count();
        assertEquals(initialCount, finalCount, "No new permissions should have been saved.");
    }
}
