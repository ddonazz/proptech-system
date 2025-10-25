package com.andrea.proptech.user.role.mapper;

import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.web.dto.request.RoleCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoleCreateDtoToRoleMapperTest {

    @InjectMocks
    private RoleCreateDtoToRoleMapper roleCreateDtoToRoleMapper;

    @Test
    @DisplayName("Should correctly map a RoleDto with permissions to a Role entity")
    void apply_withValidDto_returnsCorrectEntity() {
        final String ROLE_NAME = "ADMIN";

        RoleCreateDto roleCreateDto = RoleCreateDto.builder()
                .name(ROLE_NAME)
                .build();

        Role role = roleCreateDtoToRoleMapper.apply(roleCreateDto);

        assertNotNull(role, "The resulting Role entity should not be null.");
        assertEquals(ROLE_NAME, role.getName(), "The role name must be mapped correctly.");

        Set<Permission> permissions = role.getPermissions();
        assertNotNull(permissions, "The permissions set should be initialized, not null.");
    }

    @Test
    @DisplayName("Should throw NullPointerException if RoleDto is null")
    void apply_withNullDto_throwsException() {
        assertThrows(NullPointerException.class, () -> roleCreateDtoToRoleMapper.apply(null),
                "Passing a null DTO should throw a NullPointerException.");
    }
}
