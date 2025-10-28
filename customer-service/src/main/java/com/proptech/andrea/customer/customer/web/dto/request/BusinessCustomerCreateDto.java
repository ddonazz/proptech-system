package com.proptech.andrea.customer.customer.web.dto.request;

import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record BusinessCustomerCreateDto(
        @NotBlank
        @Email
        String email,

        String phoneNumber,

        @NotBlank
        String companyName,

        @NotBlank
        String vatNumber,

        String fiscalCode,

        @NotNull
        @Valid
        AddressCreateDto legalAddress,

        @Valid
        AddressCreateDto billingAddress,

        Set<@Valid AddressCreateDto> operationalAddresses,

        @Size(max = 7)
        String sdiCode,

        @Email
        String pecEmail
) {
}
