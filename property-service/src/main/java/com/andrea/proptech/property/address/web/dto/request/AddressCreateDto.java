package com.andrea.proptech.property.address.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressCreateDto(
        @NotBlank
        String street,

        @NotBlank
        @Size(max = 50)
        String number,

        @NotBlank
        @Size(max = 10)
        String postalCode, // CAP

        @NotBlank
        @Size(max = 100)
        String city,

        @NotBlank
        @Size(max = 100)
        String municipality, // Comune

        @NotBlank
        @Size(min = 2, max = 2)
        String province, // "MI", "RM"

        @NotBlank
        @Size(min = 2, max = 2) // ISO 3166-1 alpha-2
        String country, // "IT"

        Double latitude,
        Double longitude,
        String notes
) {
}
