package com.proptech.andrea.customer.customer.web;

import com.andrea.proptech.core.dto.PageResponse;
import com.proptech.andrea.customer.customer.service.CustomerService;
import com.proptech.andrea.customer.customer.web.dto.request.CustomerFilters;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management (General)", description = "General APIs for managing all customer types")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get all customers (paginated, basic info)")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_customer:read')")
    public ResponseEntity<PageResponse<CustomerResponseDto>> getCustomers(
            Pageable pageable,
            @ModelAttribute CustomerFilters filters) {
        Page<CustomerResponseDto> customerDtosPage = customerService.getCustomers(filters, pageable);

        PageResponse<CustomerResponseDto> response = PageResponse.fromPage(customerDtosPage);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete any customer (private or business)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_customer:delete')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}