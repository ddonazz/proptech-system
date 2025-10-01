package com.andrea.user.role.mapper;

import com.andrea.user.permission.mapper.PermissionToPermissionDtoMapper;
import com.andrea.user.role.data.Role;
import com.andrea.user.role.web.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleToRoleDtoMapper implements Function<Role, RoleDto> {

    private final PermissionToPermissionDtoMapper permissionToPermissionDtoMapper;

    @Override
    public RoleDto apply(@NonNull Role role) {
        return RoleDto.builder()
                .name(role.getName())
                .permissions(role.getPermissions()
                        .stream()
                        .map(permissionToPermissionDtoMapper)
                        .collect(Collectors.toSet()))
                .build();
    }
}
