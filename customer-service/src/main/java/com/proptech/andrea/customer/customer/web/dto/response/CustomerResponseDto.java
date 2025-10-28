package com.proptech.andrea.customer.customer.web.dto.response;

import com.proptech.andrea.customer.customer.data.CustomerType;
import lombok.Builder;

@Builder
public record CustomerResponseDto(
        Long id,
        CustomerType customerType,
        String email,
        String displayName
) {
}
