package com.andrea.proptech.property.unit.web.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EnergyCertificateResponseDto(
        Long id,
        String certificateIdentifier,
        String energyClass,
        Double globalPerformanceIndex,
        LocalDate issueDate,
        LocalDate expiryDate
) {
}
