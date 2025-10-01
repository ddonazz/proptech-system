package com.andrea.user.permission.web.dto;

import lombok.Builder;

@Builder
public record PermissionDto(
        String name,
        String description
) {
}
