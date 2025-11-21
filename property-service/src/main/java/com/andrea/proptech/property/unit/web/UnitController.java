package com.andrea.proptech.property.unit.web;

import com.andrea.proptech.core.dto.PageResponse;
import com.andrea.proptech.property.unit.service.UnitService;
import com.andrea.proptech.property.unit.web.dto.request.UnitCreateDto;
import com.andrea.proptech.property.unit.web.dto.request.UnitUpdateDto;
import com.andrea.proptech.property.unit.web.dto.response.UnitResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/properties/{propertyId}/units")
@RequiredArgsConstructor
@Tag(name = "Unit Management", description = "APIs for managing units within a property")
public class UnitController {

    private final UnitService unitService;

    @Operation(summary = "Create a new unit for a property")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_property:create')")
    public ResponseEntity<UnitResponseDto> createUnit(
            @PathVariable Long propertyId,
            @Validated @RequestBody UnitCreateDto request) {

        UnitResponseDto response = unitService.createUnit(propertyId, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get a specific unit by ID")
    @GetMapping("/{unitId}")
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<UnitResponseDto> getUnitById(
            @PathVariable Long propertyId,
            @PathVariable Long unitId) {

        UnitResponseDto response = unitService.getUnitById(propertyId, unitId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all units for a specific property (paginated)")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<PageResponse<UnitResponseDto>> getAllUnitsForProperty(
            @PathVariable Long propertyId,
            Pageable pageable) {

        Page<UnitResponseDto> unitPage = unitService.getAllUnitsForProperty(propertyId, pageable);
        PageResponse<UnitResponseDto> response = PageResponse.fromPage(unitPage);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a unit's main details")
    @PutMapping("/{unitId}")
    @PreAuthorize("hasAuthority('SCOPE_property:update')")
    public ResponseEntity<UnitResponseDto> updateUnit(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @Validated @RequestBody UnitUpdateDto request) {

        UnitResponseDto response = unitService.updateUnit(propertyId, unitId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a unit")
    @DeleteMapping("/{unitId}")
    @PreAuthorize("hasAuthority('SCOPE_property:delete')")
    public ResponseEntity<Void> deleteUnit(
            @PathVariable Long propertyId,
            @PathVariable Long unitId) {

        unitService.deleteUnit(propertyId, unitId);
        return ResponseEntity.noContent().build();
    }
}