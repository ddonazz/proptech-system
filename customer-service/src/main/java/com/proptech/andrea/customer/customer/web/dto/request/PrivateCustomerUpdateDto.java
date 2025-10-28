package com.proptech.andrea.customer.customer.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PrivateCustomerUpdateDto(
        @NotBlank
        @Email
        String email,

        String phoneNumber,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        LocalDate birthDate,

        String birthPlace,

        String nationality
) {
}
