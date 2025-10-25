package com.andrea.proptech.user.role.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.Set;

@Builder
public record RoleUpdateDto(

        @NotEmpty
        Set<Long> permissions
) {
}
