package com.andrea.proptech.user.permission.web.dto.response;

import lombok.Builder;

@Builder
public record PermissionResponseDto(
        Long id,
        String authority,
        String description
) {
}
