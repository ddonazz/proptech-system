package com.andrea.proptech.user.role.mapper;

import com.andrea.proptech.user.permission.mapper.PermissionToPermissionResponseDtoMapper;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.web.dto.response.RoleResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleToRoleResponseDtoMapper implements Function<Role, RoleResponseDto> {

    private final PermissionToPermissionResponseDtoMapper permissionToPermissionResponseDtoMapper;

    @Override
    public RoleResponseDto apply(@NonNull Role role) {
        return RoleResponseDto.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions()
                        .stream()
                        .map(permissionToPermissionResponseDtoMapper)
                        .collect(Collectors.toSet()))
                .build();
    }
}
