package com.proptech.andrea.customer.address.web.dto.response;

import lombok.Builder;

@Builder
public record AddressResponseDto(
        Long id,
        String street,
        String number,
        String postalCode,
        String city,
        String province,
        String country
) {
}
