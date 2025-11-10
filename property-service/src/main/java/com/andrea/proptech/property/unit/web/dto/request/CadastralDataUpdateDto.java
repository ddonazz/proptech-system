package com.andrea.proptech.property.unit.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastralDataUpdateDto(
        @NotBlank
        String sheet,

        @NotBlank
        String parcel,

        @NotBlank
        String subordinate,

        @NotBlank
        @Size(max = 10)
        String category,

        @Size(max = 10)
        String buildingClass,

        String consistency,

        @Positive
        Double cadastralIncome
) {
}