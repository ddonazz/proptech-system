package com.proptech.andrea.customer.customer.web;

import com.proptech.andrea.customer.customer.service.IndividualCustomerService;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerCreateDto;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerUpdateDto;
import com.proptech.andrea.customer.customer.web.dto.response.individual.IndividualCustomerResponseDto;
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
@RequestMapping("/api/v1/customers/individual")
@RequiredArgsConstructor
@Tag(name = "Customer Management (Individual)", description = "APIs for managing individual customers")
public class IndividualCustomerController {

    private final IndividualCustomerService individualCustomerService;

    @Operation(summary = "Get a individual customer by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:read')")
    public ResponseEntity<IndividualCustomerResponseDto> getPrivateCustomerById(@PathVariable Long id) {
        IndividualCustomerResponseDto response = individualCustomerService.getPrivateCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new individual customer")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<IndividualCustomerResponseDto> createPrivateCustomer(@Validated @RequestBody IndividualCustomerCreateDto request) {
        IndividualCustomerResponseDto response = individualCustomerService.createPrivateCustomer(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Update a individual customer's main details")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:write')")
    public ResponseEntity<IndividualCustomerResponseDto> updatePrivateCustomer(@PathVariable Long id, @Validated @RequestBody IndividualCustomerUpdateDto request) {
        IndividualCustomerResponseDto response = individualCustomerService.updatePrivateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

}
