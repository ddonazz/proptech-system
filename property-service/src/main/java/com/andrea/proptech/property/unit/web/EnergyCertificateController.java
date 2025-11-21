package com.andrea.proptech.property.unit.web;

import com.andrea.proptech.property.unit.service.EnergyCertificateService;
import com.andrea.proptech.property.unit.web.dto.request.EnergyCertificateDto;
import com.andrea.proptech.property.unit.web.dto.response.EnergyCertificateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/properties/{propertyId}/units/{unitId}/energy-certificate")
@RequiredArgsConstructor
@Tag(name = "Unit Management (Energy)", description = "APIs for managing the energy certificate (APE) for a specific unit")
public class EnergyCertificateController {

    private final EnergyCertificateService certificateService;

    @Operation(summary = "Add an energy certificate to a unit (One-to-One)")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_property:create')")
    public ResponseEntity<EnergyCertificateResponseDto> createEnergyCertificate(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @Validated @RequestBody EnergyCertificateDto request) {

        EnergyCertificateResponseDto response = certificateService.createEnergyCertificate(propertyId, unitId, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get the energy certificate for a unit")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<EnergyCertificateResponseDto> getEnergyCertificate(
            @PathVariable Long propertyId,
            @PathVariable Long unitId) {

        EnergyCertificateResponseDto response = certificateService.getEnergyCertificate(propertyId, unitId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update the energy certificate for a unit")
    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_property:update')")
    public ResponseEntity<EnergyCertificateResponseDto> updateEnergyCertificate(
            @PathVariable Long propertyId,
            @PathVariable Long unitId,
            @Validated @RequestBody EnergyCertificateDto request) {

        EnergyCertificateResponseDto response = certificateService.updateEnergyCertificate(propertyId, unitId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete the energy certificate from a unit")
    @DeleteMapping
    @PreAuthorize("hasAuthority('SCOPE_property:delete')")
    public ResponseEntity<Void> deleteEnergyCertificate(
            @PathVariable Long propertyId,
            @PathVariable Long unitId) {

        certificateService.deleteEnergyCertificate(propertyId, unitId);
        return ResponseEntity.noContent().build();
    }
}