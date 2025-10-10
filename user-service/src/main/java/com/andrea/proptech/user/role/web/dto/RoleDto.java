package com.andrea.proptech.user.role.web.dto;


import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import com.andrea.proptech.user.validation.OnCreate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;

@Builder
public record RoleDto(

        Long id,

        @NotBlank(groups = OnCreate.class)
        @Min(value = 5, groups = OnCreate.class)
        String name,

        String description,

        Set<PermissionDto> permissions
) {
}
