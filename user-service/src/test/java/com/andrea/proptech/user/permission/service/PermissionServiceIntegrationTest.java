package com.andrea.proptech.user.permission.service;

import com.andrea.proptech.core.security.permission.PermissionAuthority;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import com.andrea.proptech.user.role.data.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
        permissionRepository.deleteAll();
    }

    @Test
    void findAll_whenPermissionsExist_shouldReturnPagedPermissions() {
        permissionRepository.save(new Permission(PermissionAuthority.USER_READ));
        permissionRepository.save(new Permission(PermissionAuthority.USER_CREATE));

        Pageable pageable = PageRequest.of(0, 10);

        Page<PermissionDto> result = permissionService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(PermissionAuthority.USER_READ.getAuthority(), result.getContent().getFirst().authority());
    }

}
