package com.andrea.proptech.property.unit.web.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EnergyCertificateDto(
        @NotBlank
        String certificateIdentifier,

        @NotBlank
        @Size(max = 10)
        String energyClass,

        Double globalPerformanceIndex,

        @NotNull
        @PastOrPresent
        LocalDate issueDate,

        @NotNull
        @Future
        LocalDate expiryDate
) {
}
