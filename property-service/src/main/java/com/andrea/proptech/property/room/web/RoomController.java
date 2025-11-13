package com.andrea.proptech.property.room.web;

import com.andrea.proptech.property.room.service.RoomService;
import com.andrea.proptech.property.room.web.dto.request.RoomCreateDto;
import com.andrea.proptech.property.room.web.dto.request.RoomUpdateDto;
import com.andrea.proptech.property.room.web.dto.response.RoomResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties/{propertyId}/units/{unitId}/rooms")
@RequiredArgsConstructor
@Tag(name = "Unit Management (Rooms)", description = "APIs for managing rooms within a specific unit")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "Add a new room to a unit")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_property:create')")
    public ResponseEntity<RoomResponseDto> createRoom(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @Validated @RequestBody RoomCreateDto request) {

        RoomResponseDto response = roomService.createRoom(propertyId, unitId, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get a specific room by ID")
    @GetMapping("/{roomId}")
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<RoomResponseDto> getRoomById(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @PathVariable Long roomId) {

        RoomResponseDto response = roomService.getRoomById(propertyId, unitId, roomId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all rooms for a specific unit")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<List<RoomResponseDto>> getAllRoomsForUnit(
            @PathVariable Long propertyId,
            @PathVariable Long unitId) {

        List<RoomResponseDto> response = roomService.getAllRoomsForUnit(propertyId, unitId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a room's details")
    @PutMapping("/{roomId}")
    @PreAuthorize("hasAuthority('SCOPE_property:update')")
    public ResponseEntity<RoomResponseDto> updateRoom(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @PathVariable Long roomId,
            @Validated @RequestBody RoomUpdateDto request) {

        RoomResponseDto response = roomService.updateRoom(propertyId, unitId, roomId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a room from a unit")
    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasAuthority('SCOPE_property:delete')")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @PathVariable Long roomId) {

        roomService.deleteRoom(propertyId, unitId, roomId);
        return ResponseEntity.noContent().build();
    }
}