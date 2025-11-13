package com.andrea.proptech.property.unit.web;

import com.andrea.proptech.property.unit.service.CadastralDataService;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataCreateDto;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataUpdateDto;
import com.andrea.proptech.property.unit.web.dto.response.CadastralDataResponseDto;
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
@RequestMapping("/api/v1/properties/{propertyId}/units/{unitId}/cadastral-data")
@RequiredArgsConstructor
@Tag(name = "Unit Management (Cadastral)", description = "APIs for managing cadastral data for a specific unit")
public class CadastralDataController {

    private final CadastralDataService cadastralDataService;

    @Operation(summary = "Add new cadastral data to a unit")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_property:create')")
    public ResponseEntity<CadastralDataResponseDto> createCadastralData(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @Validated @RequestBody CadastralDataCreateDto request) {

        CadastralDataResponseDto response = cadastralDataService.createCadastralData(propertyId, unitId, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get specific cadastral data by ID")
    @GetMapping("/{dataId}")
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<CadastralDataResponseDto> getCadastralDataById(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @PathVariable Long dataId) {

        CadastralDataResponseDto response = cadastralDataService.getCadastralDataById(propertyId, unitId, dataId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all cadastral data for a specific unit")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<List<CadastralDataResponseDto>> getAllCadastralDataForUnit(
            @PathVariable Long propertyId,
            @PathVariable Long unitId) {

        List<CadastralDataResponseDto> response = cadastralDataService.getAllCadastralDataForUnit(propertyId, unitId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update cadastral data details")
    @PutMapping("/{dataId}")
    @PreAuthorize("hasAuthority('SCOPE_property:update')")
    public ResponseEntity<CadastralDataResponseDto> updateCadastralData(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @PathVariable Long dataId,
            @Validated @RequestBody CadastralDataUpdateDto request) {

        CadastralDataResponseDto response = cadastralDataService.updateCadastralData(propertyId, unitId, dataId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete cadastral data from a unit")
    @DeleteMapping("/{dataId}")
    @PreAuthorize("hasAuthority('SCOPE_property:delete')")
    public ResponseEntity<Void> deleteCadastralData(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @PathVariable Long dataId) {

        cadastralDataService.deleteCadastralData(propertyId, unitId, dataId);
        return ResponseEntity.noContent().build();
    }
}