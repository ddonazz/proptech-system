package com.andrea.proptech.property.property.web;

import com.andrea.proptech.property.property.service.PropertyService;
import com.andrea.proptech.property.property.web.dto.request.PropertyCreateDto;
import com.andrea.proptech.property.property.web.dto.request.PropertyUpdateDto;
import com.andrea.proptech.property.property.web.dto.response.PropertyResponseDto;
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
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
@Tag(name = "Property Management", description = "APIs for managing properties (buildings, structures)")
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(summary = "Create a new property structure")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_property:create')")
    public ResponseEntity<PropertyResponseDto> createProperty(@Validated @RequestBody PropertyCreateDto request) {

        PropertyResponseDto response = propertyService.createProperty(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get a property by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_property:read')")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        PropertyResponseDto response = propertyService.getPropertyById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a property's details")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_property:update')")
    public ResponseEntity<PropertyResponseDto> updateProperty(
            @PathVariable Long id,
            @Validated @RequestBody PropertyUpdateDto request) {

        PropertyResponseDto response = propertyService.updateProperty(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a property")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_property:delete')")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

}