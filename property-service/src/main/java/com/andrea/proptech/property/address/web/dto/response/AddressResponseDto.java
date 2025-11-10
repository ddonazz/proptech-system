package com.andrea.proptech.property.address.web.dto.response;

import lombok.Builder;

@Builder
public record AddressResponseDto(
        Long id,
        String street,
        String number,
        String postalCode,
        String city,
        String municipality,
        String province,
        String country,
        Double latitude,
        Double longitude,
        String notes
) {
}