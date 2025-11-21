package com.andrea.proptech.property.unit.mapper.request;

import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.web.dto.request.UnitUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class UnitUpdateDtoToUnitMapper implements BiFunction<UnitUpdateDto, Unit, Unit> {

    @Override
    public Unit apply(@NonNull UnitUpdateDto dto, @NonNull Unit unit) {

        unit.setInternalName(dto.internalName());
        unit.setInternalCode(dto.internalCode());
        unit.setType(dto.type());
        unit.setFloor(dto.floor());
        unit.setInternalNumber(dto.internalNumber());
        unit.setTotalAreaMq(dto.totalAreaMq());
        unit.setWalkableAreaMq(dto.walkableAreaMq());
        unit.setRoomCount(dto.roomCount());
        unit.setBedroomCount(dto.bedroomCount());
        unit.setBathroomCount(dto.bathroomCount());
        unit.setMaxOccupancy(dto.maxOccupancy());
        unit.setRegionalIdentifierCode(dto.regionalIdentifierCode());

        unit.setAmenities(dto.amenities() != null ? dto.amenities() : Collections.emptySet());

        return unit;
    }
}