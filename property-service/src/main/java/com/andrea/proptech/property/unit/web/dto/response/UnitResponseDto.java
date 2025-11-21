package com.andrea.proptech.property.unit.web.dto.response;

import com.andrea.proptech.core.dto.TranslatedEnum;
import com.andrea.proptech.property.room.web.dto.response.RoomResponseDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record UnitResponseDto(
        Long id,
        Long propertyId,
        Long ownerId,
        String internalName,
        String internalCode,
        TranslatedEnum type,
        String floor,
        String internalNumber,
        Double totalAreaMq,
        Double walkableAreaMq,
        Integer roomCount,
        Integer bedroomCount,
        Integer bathroomCount,
        Integer maxOccupancy,
        String regionalIdentifierCode,
        Set<TranslatedEnum> amenities,
        Set<RoomResponseDto> rooms,
        Set<CadastralDataResponseDto> cadastralData,
        EnergyCertificateResponseDto energyCertificate
) {
}
