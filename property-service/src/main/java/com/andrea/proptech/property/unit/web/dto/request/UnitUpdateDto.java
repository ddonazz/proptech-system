package com.andrea.proptech.property.unit.web.dto.request;

import com.andrea.proptech.property.unit.data.UnitAmenity;
import com.andrea.proptech.property.unit.data.UnitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record UnitUpdateDto(
        @NotBlank
        String internalName,

        String internalCode,

        @NotNull
        UnitType type,

        @NotBlank
        String floor,

        String internalNumber,

        @NotNull
        @Positive
        Double totalAreaMq,

        Double walkableAreaMq,

        @NotNull
        @Positive
        Integer roomCount,

        @NotNull
        @Positive
        Integer bedroomCount,

        @NotNull
        @Positive
        Integer bathroomCount,

        @Positive
        Integer maxOccupancy,

        String regionalIdentifierCode,

        Set<UnitAmenity> amenities
) {
}