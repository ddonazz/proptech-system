package com.andrea.proptech.user.role.mapper;

import com.andrea.proptech.user.permission.PermissionName;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.mapper.PermissionDtoToPermissionMapper;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.web.dto.RoleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleDtoToRoleMapperTest {

    @Mock
    private PermissionDtoToPermissionMapper permissionDtoToPermissionMapper;

    @InjectMocks
    private RoleDtoToRoleMapper roleDtoToRoleMapper;

    @Test
    @DisplayName("Should correctly map a RoleDto with permissions to a Role entity")
    void apply_withValidDtoAndPermissions_returnsCorrectEntity() {
        final String ROLE_NAME = "ADMIN";
        final String PERM_1 = PermissionName.ADMIN_ACCESS.name();
        final String PERM_2 = PermissionName.USER_READ.name();

        PermissionDto permissionDto1 = PermissionDto.builder().name(PERM_1).build();
        PermissionDto permissionDto2 = PermissionDto.builder().name(PERM_2).build();

        when(permissionDtoToPermissionMapper.apply(permissionDto1)).thenReturn(new Permission(PermissionName.valueOf(permissionDto1.name())));
        when(permissionDtoToPermissionMapper.apply(permissionDto2)).thenReturn(new Permission(PermissionName.valueOf(permissionDto2.name())));

        RoleDto roleDto = RoleDto.builder()
                .name(ROLE_NAME)
                .permissions(Set.of(permissionDto1, permissionDto2))
                .build();

        Role role = roleDtoToRoleMapper.apply(roleDto);

        assertNotNull(role, "The resulting Role entity should not be null.");
        assertEquals(ROLE_NAME, role.getName(), "The role name must be mapped correctly.");

        Set<Permission> permissions = role.getPermissions();
        assertNotNull(permissions, "The permissions set should be initialized, not null.");
        assertEquals(2, permissions.size(), "The number of mapped permissions is incorrect.");
        assertTrue(permissions.stream().anyMatch(p -> p.getName().equals(PERM_1)), "Permission 1 was not mapped correctly.");
        assertTrue(permissions.stream().anyMatch(p -> p.getName().equals(PERM_2)), "Permission 2 was not mapped correctly.");
    }

    @Test
    @DisplayName("Should correctly map a RoleDto with empty permissions")
    void apply_withEmptyPermissions_returnsEntityWithEmptySet() {
        RoleDto roleDto = RoleDto.builder()
                .name("GUEST")
                .permissions(Collections.emptySet())
                .build();

        Role role = roleDtoToRoleMapper.apply(roleDto);

        assertNotNull(role, "The resulting Role entity should not be null.");
        assertEquals("GUEST", role.getName(), "The role name must be mapped correctly.");

        Set<Permission> permissions = role.getPermissions();
        assertNotNull(permissions, "The permissions set should be initialized (not null).");
        assertTrue(permissions.isEmpty(), "The permissions set should be empty.");
    }

    @Test
    @DisplayName("Should throw NullPointerException if RoleDto is null")
    void apply_withNullDto_throwsException() {
        assertThrows(NullPointerException.class, () -> roleDtoToRoleMapper.apply(null),
                "Passing a null DTO should throw a NullPointerException.");
    }
}
