package com.andrea.proptech.property.room.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.exception.PropertyErrorCodes;
import com.andrea.proptech.property.room.data.Room;
import com.andrea.proptech.property.room.data.RoomRepository;
import com.andrea.proptech.property.room.mapper.request.RoomCreateDtoToRoomMapper;
import com.andrea.proptech.property.room.mapper.request.RoomUpdateDtoToRoomMapper;
import com.andrea.proptech.property.room.mapper.response.RoomToRoomResponseDtoMapper;
import com.andrea.proptech.property.room.web.dto.request.RoomCreateDto;
import com.andrea.proptech.property.room.web.dto.response.RoomResponseDto;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private RoomCreateDtoToRoomMapper createMapper;
    @Mock
    private RoomUpdateDtoToRoomMapper updateMapper;
    @Mock
    private RoomToRoomResponseDtoMapper responseMapper;

    @InjectMocks
    private RoomService roomService;

    @Test
    @DisplayName("createRoom should throw exception if parent Unit not found")
    void createRoom_unitNotFound_shouldThrowException() {
        // Given
        Long propertyId = 1L;
        Long unitId = 999L;
        when(unitRepository.findByIdAndPropertyId(unitId, propertyId)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> roomService.createRoom(propertyId, unitId, mock(RoomCreateDto.class)));

        assertEquals(PropertyErrorCodes.UNIT_NOT_FOUND.getErrorCode(), ex.getErrorCode());
        verify(roomRepository, never()).save(any());
    }

    @Test
    @DisplayName("createRoom should link room to unit and save")
    void createRoom_success() {
        // Given
        Long propertyId = 1L;
        Long unitId = 10L;
        Unit unit = new Unit();
        unit.setId(unitId);

        Room room = new Room();
        Room savedRoom = new Room();
        savedRoom.setId(100L);

        when(unitRepository.findByIdAndPropertyId(unitId, propertyId)).thenReturn(Optional.of(unit));
        when(createMapper.apply(any(RoomCreateDto.class))).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(savedRoom);
        when(responseMapper.apply(savedRoom)).thenReturn(RoomResponseDto.builder().id(100L).build());

        // When
        RoomResponseDto result = roomService.createRoom(propertyId, unitId, mock(RoomCreateDto.class));

        // Then
        assertNotNull(result);
        assertEquals(100L, result.id());
        assertEquals(unit, room.getUnit()); // Verifica link fondamentale
        verify(roomRepository).save(room);
    }

    @Test
    @DisplayName("deleteRoom should delete existing room")
    void deleteRoom_success() {
        // Given
        Long propertyId = 1L;
        Long unitId = 10L;
        Long roomId = 100L;

        Unit unit = new Unit();
        Room room = new Room();

        when(unitRepository.findByIdAndPropertyId(unitId, propertyId)).thenReturn(Optional.of(unit));
        when(roomRepository.findByIdAndUnitId(roomId, unitId)).thenReturn(Optional.of(room));

        // When
        roomService.deleteRoom(propertyId, unitId, roomId);

        // Then
        verify(roomRepository).delete(room);
    }
}