package com.andrea.proptech.property.unit.mapper.request;

import com.andrea.proptech.property.room.mapper.request.RoomCreateDtoToRoomMapper;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.web.dto.request.UnitCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitCreateDtoToUnitMapper implements Function<UnitCreateDto, Unit> {

    private final RoomCreateDtoToRoomMapper roomCreateDtoToRoomMapper;
    private final CadastralDataCreateDtoToCadastralDataMapper cadastralDataCreateDtoToCadastralDataMapper;
    private final EnergyCertificateDtoToEnergyCertificateMapper energyCertificateDtoToEnergyCertificateMapper;

    @Override
    public Unit apply(@NonNull UnitCreateDto dto) {
        Unit unit = new Unit();

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

        unit.setRooms(dto.rooms() != null ?
                dto.rooms().stream()
                        .map(roomCreateDtoToRoomMapper)
                        .peek(room -> room.setUnit(unit))
                        .collect(Collectors.toSet())
                : Collections.emptySet());

        unit.setCadastralData(dto.cadastralData() != null ?
                dto.cadastralData().stream()
                        .map(cadastralDataCreateDtoToCadastralDataMapper)
                        .peek(data -> data.setUnit(unit))
                        .collect(Collectors.toSet())
                : Collections.emptySet());

        if (dto.energyCertificate() != null) {
            unit.setEnergyCertificate(energyCertificateDtoToEnergyCertificateMapper.apply(dto.energyCertificate()));
            unit.getEnergyCertificate().setUnit(unit);
        }

        return unit;
    }
}