package com.andrea.proptech.user.user.web.dto.response;

import com.andrea.proptech.user.role.web.dto.response.RoleResponseDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserResponseDto(
        Long id,
        String username,
        String email,
        Set<RoleResponseDto> roles
) {
}
