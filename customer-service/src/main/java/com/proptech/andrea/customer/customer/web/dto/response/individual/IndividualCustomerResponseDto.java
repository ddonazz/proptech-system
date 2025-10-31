package com.proptech.andrea.customer.customer.web.dto.response.individual;

import com.proptech.andrea.customer.address.web.dto.response.AddressResponseDto;
import com.proptech.andrea.customer.customer.data.CustomerType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record IndividualCustomerResponseDto(
        Long id,
        String email,
        String phoneNumber,
        Long userId,
        CustomerType customerType,
        String firstName,
        String lastName,
        String fiscalCode,
        LocalDate birthDate,
        String birthPlace,
        String nationality,
        AddressResponseDto billingAddress
) {
}
