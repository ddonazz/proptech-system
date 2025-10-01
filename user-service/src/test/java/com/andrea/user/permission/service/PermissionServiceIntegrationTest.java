package com.andrea.user.permission.service;

import com.andrea.user.permission.PermissionName;
import com.andrea.user.permission.data.Permission;
import com.andrea.user.permission.data.PermissionRepository;
import com.andrea.user.permission.web.dto.PermissionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PermissionServiceIntegrationTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setUp() {
        permissionRepository.deleteAll();
    }

    @Test
    void findAll_whenPermissionsExist_shouldReturnPagedPermissions() {
        permissionRepository.save(new Permission(PermissionName.USER_READ));
        permissionRepository.save(new Permission(PermissionName.USER_CREATE));

        Pageable pageable = PageRequest.of(0, 10);

        Page<PermissionDto> result = permissionService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(PermissionName.USER_READ.name(), result.getContent().getFirst().name());
    }

}
