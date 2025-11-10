package com.andrea.proptech.property.property.web.dto.request;

import com.andrea.proptech.property.address.web.dto.request.AddressCreateDto;
import com.andrea.proptech.property.property.data.BuildingAmenity;
import com.andrea.proptech.property.property.data.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record PropertyCreateDto(
        @NotBlank
        String name,

        @NotNull
        PropertyType type,

        @NotNull
        @Valid
        AddressCreateDto address,

        @Positive
        Integer constructionYear,

        Set<BuildingAmenity> amenities
) {
}