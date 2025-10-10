package com.andrea.proptech.user.role.mapper;

import com.andrea.proptech.user.permission.mapper.PermissionDtoToPermissionMapper;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.web.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleDtoToRoleMapper implements Function<RoleDto, Role> {

    private final PermissionDtoToPermissionMapper permissionDtoToPermissionMapper;

    @Override
    public Role apply(@NonNull RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.name());
        role.setDescription(roleDto.description());
        role.setPermissions(roleDto.permissions()
                .stream()
                .map(permissionDtoToPermissionMapper)
                .collect(Collectors.toSet()));

        return role;
    }
}
