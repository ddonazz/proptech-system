package com.proptech.andrea.customer.customer.web.dto.request.individual;

import com.proptech.andrea.customer.address.web.dto.request.AddressUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record IndividualCustomerUpdateDto(
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

        String nationality,

        @Valid
        AddressUpdateDto billingAddress
) {
}
