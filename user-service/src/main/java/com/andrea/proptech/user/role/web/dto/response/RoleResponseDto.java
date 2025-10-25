package com.andrea.proptech.user.role.web.dto.response;

import com.andrea.proptech.user.permission.web.dto.response.PermissionResponseDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record RoleResponseDto(
        Long id,

        String name,

        String description,

        Set<PermissionResponseDto> permissions
) {
}
