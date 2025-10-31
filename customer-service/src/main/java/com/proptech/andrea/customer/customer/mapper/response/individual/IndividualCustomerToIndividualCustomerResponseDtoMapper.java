package com.proptech.andrea.customer.customer.mapper.response.individual;

import com.proptech.andrea.customer.address.mapper.AddressToAddressResponseDtoMapper;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.web.dto.response.individual.IndividualCustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class IndividualCustomerToIndividualCustomerResponseDtoMapper implements Function<IndividualCustomer, IndividualCustomerResponseDto> {

    private final AddressToAddressResponseDtoMapper addressToAddressResponseDtoMapper;

    @Override
    public IndividualCustomerResponseDto apply(IndividualCustomer customer) {
        return IndividualCustomerResponseDto.builder()
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
