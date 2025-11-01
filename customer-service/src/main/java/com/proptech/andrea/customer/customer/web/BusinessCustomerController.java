package com.proptech.andrea.customer.customer.web;

import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import com.proptech.andrea.customer.address.web.dto.response.AddressResponseDto;
import com.proptech.andrea.customer.customer.service.BusinessCustomerService;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerCreateDto;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerUpdateDto;
import com.proptech.andrea.customer.customer.web.dto.request.business.CustomerContactCreateDto;
import com.proptech.andrea.customer.customer.web.dto.response.business.BusinessCustomerResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.business.CustomerContactResponseDto;
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
@RequestMapping("/api/v1/customers/business")
@RequiredArgsConstructor
@Tag(name = "Customer Management (Business)", description = "APIs for managing business customers and their contacts/addresses")
public class BusinessCustomerController {

    private final BusinessCustomerService businessCustomerService;

    @Operation(summary = "Get a business customer by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:read')")
    public ResponseEntity<BusinessCustomerResponseDto> getBusinessCustomerById(@PathVariable Long id) {
        BusinessCustomerResponseDto response = businessCustomerService.getBusinessCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new business customer")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_customer:create')")
    public ResponseEntity<BusinessCustomerResponseDto> createBusinessCustomer(@Validated @RequestBody BusinessCustomerCreateDto request) {
        BusinessCustomerResponseDto response = businessCustomerService.createBusinessCustomer(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Update a business customer's main details")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:update')")
    public ResponseEntity<BusinessCustomerResponseDto> updateBusinessCustomer(
            @PathVariable Long id,
            @Validated @RequestBody BusinessCustomerUpdateDto request) {

        BusinessCustomerResponseDto response = businessCustomerService.updateBusinessCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    // --- Contact Management ---

    @Operation(summary = "Create a new contact for a business customer")
    @PostMapping("/{businessId}/contacts")
    @PreAuthorize("hasAuthority('SCOPE_customer:create')")
    public ResponseEntity<CustomerContactResponseDto> createContactForBusiness(
            @PathVariable Long businessId, @Validated @RequestBody CustomerContactCreateDto request) {

        CustomerContactResponseDto response = businessCustomerService.createContactForBusiness(businessId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Delete a contact from a business customer")
    @DeleteMapping("/{businessId}/contacts/{contactId}")
    @PreAuthorize("hasAuthority('SCOPE_customer:delete')")
    public ResponseEntity<Void> deleteContact(
            @PathVariable Long businessId, @PathVariable Long contactId) {

        businessCustomerService.deleteContact(businessId, contactId);
        return ResponseEntity.noContent().build();
    }

    // --- Address Management ---

    @Operation(summary = "Create a new operational address for a business customer")
    @PostMapping("/{businessId}/operational-addresses")
    @PreAuthorize("hasAuthority('SCOPE_customer:create')")
    public ResponseEntity<AddressResponseDto> createOperationalAddressForBusiness(
            @PathVariable Long businessId, @Validated @RequestBody AddressCreateDto request) {
        AddressResponseDto response = businessCustomerService.createOperationalAddressForBusiness(businessId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Delete an operational address from a business customer")
    @DeleteMapping("/{businessId}/operational-addresses/{operationalAddressId}")
    @PreAuthorize("hasAuthority('SCOPE_customer:delete')")
    public ResponseEntity<Void> deleteOperationalAddress(
            @PathVariable Long businessId, @PathVariable Long operationalAddressId) {

        businessCustomerService.deleteOperationalAddress(businessId, operationalAddressId);
        return ResponseEntity.noContent().build();
    }
}