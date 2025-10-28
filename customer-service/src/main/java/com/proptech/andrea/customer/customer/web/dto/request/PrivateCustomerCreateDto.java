package com.proptech.andrea.customer.customer.web.dto.request;

import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PrivateCustomerCreateDto(
        @NotBlank
        @Email
        String email,

        String phoneNumber,

        Long userId,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String fiscalCode,

        LocalDate birthDate,

        String birthPlace,

        String nationality,

        @NotNull
        @Valid
        AddressCreateDto billingAddress
) {
}
