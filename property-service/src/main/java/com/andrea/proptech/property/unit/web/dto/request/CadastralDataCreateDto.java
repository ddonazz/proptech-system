package com.andrea.proptech.property.unit.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastralDataCreateDto(
        @NotBlank
        String sheet, // Foglio

        @NotBlank
        String parcel, // Particella

        @NotBlank
        String subordinate, // Subalterno

        @NotBlank
        @Size(max = 10)
        String category, // "A/2"

        @Size(max = 10)
        String buildingClass, // "3"

        String consistency, // "5 vani"

        @Positive
        Double cadastralIncome // Rendita
) {
}