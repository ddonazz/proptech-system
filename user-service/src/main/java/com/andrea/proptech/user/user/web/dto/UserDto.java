package com.andrea.proptech.user.user.web.dto;

import com.andrea.proptech.user.role.web.dto.RoleDto;
import com.andrea.proptech.user.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Long id,

        @NotBlank(groups = OnCreate.class)
        @Size(min = 5, groups = OnCreate.class)
        String username,

        @NotBlank(groups = OnCreate.class)
        @Size(min = 8, groups = {OnCreate.class})
        String password,

        Set<RoleDto> roles
) {
}
