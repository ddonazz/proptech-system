package com.andrea.proptech.property.unit.web.dto.request;

import com.andrea.proptech.property.room.web.dto.request.RoomCreateDto;
import com.andrea.proptech.property.unit.data.UnitAmenity;
import com.andrea.proptech.property.unit.data.UnitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record UnitCreateDto(
        @NotBlank
        String internalName,

        String internalCode, // SKU, es. "P-001"

        @NotNull
        UnitType type,

        @NotBlank
        String floor, // "PT", "1", "Attico"

        String internalNumber, // "12B", "A"

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

        String regionalIdentifierCode, // CIR / CIS

        Set<UnitAmenity> amenities,

        @Valid
        Set<RoomCreateDto> rooms,

        @Valid
        Set<CadastralDataCreateDto> cadastralData,

        @Valid
        EnergyCertificateDto energyCertificate
) {
}