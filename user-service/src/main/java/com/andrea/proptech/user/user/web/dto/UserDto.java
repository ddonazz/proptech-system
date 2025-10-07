package com.andrea.proptech.user.user.web.dto;

import com.andrea.proptech.user.role.web.dto.RoleDto;
import com.andrea.proptech.user.validation.OnCreate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserDto(
        long id,

        @NotBlank(groups = OnCreate.class)
        @Min(value = 5, groups = OnCreate.class)
        String username,

        @NotBlank(groups = OnCreate.class)
        @Min(value = 8, groups = {OnCreate.class})
        String password,

        Set<RoleDto> roles
) {
}
