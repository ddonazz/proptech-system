package com.andrea.proptech.property.unit.web.dto.response;

import lombok.Builder;

@Builder
public record CadastralDataResponseDto(
        Long id,
        String sheet,
        String parcel,
        String subordinate,
        String category,
        String buildingClass,
        String consistency,
        Double cadastralIncome
) {
}
