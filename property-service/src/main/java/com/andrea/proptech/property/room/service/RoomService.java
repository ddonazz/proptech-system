package com.andrea.proptech.property.room.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.exception.PropertyErrorCodes;
import com.andrea.proptech.property.room.data.Room;
import com.andrea.proptech.property.room.data.RoomRepository;
import com.andrea.proptech.property.room.mapper.request.RoomCreateDtoToRoomMapper;
import com.andrea.proptech.property.room.mapper.request.RoomUpdateDtoToRoomMapper;
import com.andrea.proptech.property.room.mapper.response.RoomToRoomResponseDtoMapper;
import com.andrea.proptech.property.room.web.dto.request.RoomCreateDto;
import com.andrea.proptech.property.room.web.dto.request.RoomUpdateDto;
import com.andrea.proptech.property.room.web.dto.response.RoomResponseDto;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UnitRepository unitRepository;
    private final RoomCreateDtoToRoomMapper roomCreateDtoToRoomMapper;
    private final RoomUpdateDtoToRoomMapper roomUpdateDtoToRoomMapper;
    private final RoomToRoomResponseDtoMapper roomToRoomResponseDtoMapper;

    @Transactional
    public RoomResponseDto createRoom(Long propertyId, Long unitId, RoomCreateDto dto) {
        Unit unit = retrieveUnit(propertyId, unitId);
        Room room = roomCreateDtoToRoomMapper.apply(dto);
        room.setUnit(unit);

        Room savedRoom = roomRepository.save(room);
        return roomToRoomResponseDtoMapper.apply(savedRoom);
    }

    @Transactional
    public RoomResponseDto updateRoom(Long propertyId, Long unitId, Long roomId, RoomUpdateDto dto) {
        retrieveUnit(propertyId, unitId);
        Room room = retrieveRoom(unitId, roomId);

        Room roomToUpdate = roomUpdateDtoToRoomMapper.apply(dto, room);
        Room updatedRoom = roomRepository.save(roomToUpdate);
        return roomToRoomResponseDtoMapper.apply(updatedRoom);
    }

    @Transactional(readOnly = true)
    public RoomResponseDto getRoomById(Long propertyId, Long unitId, Long roomId) {
        retrieveUnit(propertyId, unitId);
        Room room = retrieveRoom(unitId, roomId);
        return roomToRoomResponseDtoMapper.apply(room);
    }

    @Transactional(readOnly = true)
    public List<RoomResponseDto> getAllRoomsForUnit(Long propertyId, Long unitId) {
        retrieveUnit(propertyId, unitId);
        return roomRepository.findByUnitId(unitId).stream()
                .map(roomToRoomResponseDtoMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRoom(Long propertyId, Long unitId, Long roomId) {
        retrieveUnit(propertyId, unitId);
        Room room = retrieveRoom(unitId, roomId);
        roomRepository.delete(room);
    }

    // --- Metodi helper ---

    private Unit retrieveUnit(Long propertyId, Long unitId) {
        return unitRepository.findByIdAndPropertyId(unitId, propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.UNIT_NOT_FOUND, unitId));
    }

    private Room retrieveRoom(Long unitId, Long roomId) {
        return roomRepository.findByIdAndUnitId(roomId, unitId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.ROOM_NOT_FOUND, roomId, unitId));
    }
}