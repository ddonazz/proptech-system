package com.proptech.andrea.customer.customer.web.dto.request.business;

import com.proptech.andrea.customer.address.web.dto.request.AddressUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record BusinessCustomerUpdateDto(
        @NotBlank
        @Email
        String email,

        String phoneNumber,

        @NotBlank
        String companyName,

        @NotBlank
        String vatNumber,

        String fiscalCode,

        @Size(max = 7)
        String sdiCode,

        @Email
        String pecEmail,

        @NonNull
        @Valid
        AddressUpdateDto legalAddress,

        @NonNull
        @Valid
        AddressUpdateDto billingAddress
) {
}
