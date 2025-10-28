package com.proptech.andrea.customer.customer.web.dto.response;

import com.proptech.andrea.customer.address.web.dto.response.AddressResponseDto;
import com.proptech.andrea.customer.customer.data.CustomerType;
import lombok.Builder;

import java.util.Set;

@Builder
public record BusinessCustomerResponseDto(
        Long id,
        String email,
        String phoneNumber,
        CustomerType customerType,
        String companyName,
        String vatNumber,
        String fiscalCode,
        AddressResponseDto legalAddress,
        AddressResponseDto billingAddress,
        Set<AddressResponseDto> operationalAddresses,
        String sdiCode,
        String pecEmail,
        Set<CustomerContactResponseDto> contacts
) {
}
