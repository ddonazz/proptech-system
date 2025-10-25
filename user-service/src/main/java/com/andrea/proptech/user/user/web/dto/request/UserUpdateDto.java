package com.andrea.proptech.user.user.web.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserUpdateDto(

        @NotEmpty
        Set<Long> roles

) {
}
