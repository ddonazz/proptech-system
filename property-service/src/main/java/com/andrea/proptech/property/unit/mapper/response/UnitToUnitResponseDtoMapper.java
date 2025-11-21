package com.andrea.proptech.property.unit.mapper.response;

import com.andrea.proptech.core.dto.TranslatedEnum;
import com.andrea.proptech.property.room.mapper.response.RoomToRoomResponseDtoMapper;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitAmenity;
import com.andrea.proptech.property.unit.data.UnitType;
import com.andrea.proptech.property.unit.web.dto.response.UnitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitToUnitResponseDtoMapper implements Function<Unit, UnitResponseDto> {

    private final MessageSource messageSource;
    private final RoomToRoomResponseDtoMapper roomToRoomResponseDtoMapper;
    private final CadastralDataToCadastralDataResponseDtoMapper cadastralDataToCadastralDataResponseDtoMapper;
    private final EnergyCertificateToEnergyCertificateResponseDtoMapper energyCertificateToEnergyCertificateResponseDtoMapper;

    @Override
    public UnitResponseDto apply(@NonNull Unit unit) {
        Locale locale = LocaleContextHolder.getLocale();

        return UnitResponseDto.builder()
                .id(unit.getId())
                .propertyId(unit.getProperty().getId())
                .ownerId(unit.getOwnerId())
                .internalName(unit.getInternalName())
                .internalCode(unit.getInternalCode())
                .type(translateUnitType(unit.getType(), locale))
                .floor(unit.getFloor())
                .internalNumber(unit.getInternalNumber())
                .totalAreaMq(unit.getTotalAreaMq())
                .walkableAreaMq(unit.getWalkableAreaMq())
                .roomCount(unit.getRoomCount())
                .bedroomCount(unit.getBedroomCount())
                .bathroomCount(unit.getBathroomCount())
                .maxOccupancy(unit.getMaxOccupancy())
                .regionalIdentifierCode(unit.getRegionalIdentifierCode())
                .amenities(
                        unit.getAmenities().stream()
                                .map(amenity -> translateAmenity(amenity, locale))
                                .collect(Collectors.toSet())
                )
                .rooms(
                        unit.getRooms().stream()
                                .map(roomToRoomResponseDtoMapper)
                                .collect(Collectors.toSet())
                )
                .cadastralData(
                        unit.getCadastralData().stream()
                                .map(cadastralDataToCadastralDataResponseDtoMapper)
                                .collect(Collectors.toSet())
                )
                .energyCertificate(energyCertificateToEnergyCertificateResponseDtoMapper.apply(unit.getEnergyCertificate()))
                .build();
    }

    private TranslatedEnum translateUnitType(UnitType type, Locale locale) {
        if (type == null) {
            return null;
        }
        String code = "enum.unittype." + type.name();
        String label = messageSource.getMessage(code, null, type.name(), locale);
        return new TranslatedEnum(type.name(), label);
    }

    private TranslatedEnum translateAmenity(UnitAmenity amenity, Locale locale) {
        if (amenity == null) {
            return null;
        }
        String code = "enum.unitamenity." + amenity.name();
        String label = messageSource.getMessage(code, null, amenity.name(), locale);
        return new TranslatedEnum(amenity.name(), label);
    }
}