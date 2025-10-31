package com.proptech.andrea.customer.customer.web.dto.request.business;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerContactCreateDto(
        @NotNull
        Long userId,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Email
        String email,

        String jobTitle
) {
}
