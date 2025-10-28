package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.address.mapper.AddressToAddressResponseDtoMapper;
import com.proptech.andrea.customer.customer.data.PrivateCustomer;
import com.proptech.andrea.customer.customer.web.dto.response.PrivateCustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PrivateCustomerToPrivateCustomerResponseDtoMapper implements Function<PrivateCustomer, PrivateCustomerResponseDto> {

    private final AddressToAddressResponseDtoMapper addressToAddressResponseDtoMapper;

    @Override
    public PrivateCustomerResponseDto apply(PrivateCustomer customer) {
        return PrivateCustomerResponseDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .userId(customer.getUserId())
                .customerType(customer.getCustomerType())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fiscalCode(customer.getFiscalCode())
                .birthDate(customer.getBirthDate())
                .birthPlace(customer.getBirthPlace())
                .nationality(customer.getNationality())
                .billingAddress(addressToAddressResponseDtoMapper.apply(customer.getBillingAddress()))
                .build();
    }
}
