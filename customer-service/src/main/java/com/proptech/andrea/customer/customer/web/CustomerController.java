package com.proptech.andrea.customer.customer.web;

import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import com.proptech.andrea.customer.customer.service.CustomerService;
import com.proptech.andrea.customer.customer.web.dto.request.*;
import com.proptech.andrea.customer.customer.web.dto.response.BusinessCustomerResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerContactResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.PrivateCustomerResponseDto;
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
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customers (private and business)")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get all customers (paginated, basic info)")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_customer:read')")
    public ResponseEntity<Page<CustomerResponseDto>> getCustomers(Pageable pageable) {
        Page<CustomerResponseDto> response = customerService.getCustomers(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete any customer (private or business)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:delete')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a private customer by ID")
    @GetMapping("/private/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:read')")
    public ResponseEntity<PrivateCustomerResponseDto> getPrivateCustomerById(@PathVariable Long id) {
        PrivateCustomerResponseDto response = customerService.getPrivateCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new private customer")
    @PostMapping("/private")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<PrivateCustomerResponseDto> createPrivateCustomer(@Validated @RequestBody PrivateCustomerCreateDto request) {
        PrivateCustomerResponseDto response = customerService.createPrivateCustomer(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Update a private customer's main details")
    @PutMapping("/private/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<PrivateCustomerResponseDto> updatePrivateCustomer(@PathVariable Long id, @Validated @RequestBody PrivateCustomerUpdateDto request) {
        PrivateCustomerResponseDto response = customerService.updatePrivateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a private customer's billing address")
    @PutMapping("/private/{id}/billing-address")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<PrivateCustomerResponseDto> updatePrivateBillingAddress(@PathVariable Long id, @Validated @RequestBody AddressCreateDto request) {
        PrivateCustomerResponseDto response = customerService.updatePrivateBillingAddress(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a business customer by ID")
    @GetMapping("/business/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:read')")
    public ResponseEntity<BusinessCustomerResponseDto> getBusinessCustomerById(@PathVariable Long id) {
        BusinessCustomerResponseDto response = customerService.getBusinessCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new business customer")
    @PostMapping("/business")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<BusinessCustomerResponseDto> createBusinessCustomer(@Validated @RequestBody BusinessCustomerCreateDto request) {
        BusinessCustomerResponseDto response = customerService.createBusinessCustomer(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Update a business customer's main details")
    @PutMapping("/business/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<BusinessCustomerResponseDto> updateBusinessCustomer(@PathVariable Long id, @Validated @RequestBody BusinessCustomerUpdateDto request) {
        BusinessCustomerResponseDto response = customerService.updateBusinessCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a business customer's legal address")
    @PutMapping("/business/{id}/legal-address")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<BusinessCustomerResponseDto> updateBusinessLegalAddress(@PathVariable Long id, @Validated @RequestBody AddressCreateDto request) {
        BusinessCustomerResponseDto response = customerService.updateBusinessLegalAddress(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new contact for a business customer")
    @PostMapping("/business/{businessId}/contacts")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<CustomerContactResponseDto> createContactForBusiness(
            @PathVariable Long businessId, @Validated @RequestBody CustomerContactCreateDto request) {

        CustomerContactResponseDto response = customerService.createContactForBusiness(businessId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Delete a contact from a business customer")
    @DeleteMapping("/business/{businessId}/contacts/{contactId}")
    @PreAuthorize("hasAuthority('SCOPE_customer:delete')")
    public ResponseEntity<Void> deleteContact(
            @PathVariable Long businessId, @PathVariable Long contactId) {

        customerService.deleteContact(businessId, contactId);
        return ResponseEntity.noContent().build();
    }
    
}