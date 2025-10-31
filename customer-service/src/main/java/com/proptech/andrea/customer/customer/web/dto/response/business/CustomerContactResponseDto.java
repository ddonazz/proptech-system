package com.proptech.andrea.customer.customer.web.dto.response.business;

import lombok.Builder;

@Builder
public record CustomerContactResponseDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String email,
        String jobTitle
) {
}
