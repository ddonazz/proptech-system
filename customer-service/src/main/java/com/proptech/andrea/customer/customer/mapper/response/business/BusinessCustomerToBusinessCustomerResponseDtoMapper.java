package com.proptech.andrea.customer.customer.mapper.response.business;

import com.proptech.andrea.customer.address.mapper.AddressToAddressResponseDtoMapper;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.web.dto.response.business.BusinessCustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BusinessCustomerToBusinessCustomerResponseDtoMapper implements Function<BusinessCustomer, BusinessCustomerResponseDto> {

    private final AddressToAddressResponseDtoMapper addressToAddressResponseDtoMapper;
    private final CustomerContactToCustomerContactResponseDtoMapper customerContactToCustomerContactResponseDtoMapper;

    @Override
    public BusinessCustomerResponseDto apply(@NonNull BusinessCustomer customer) {
        return BusinessCustomerResponseDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .customerType(customer.getCustomerType())
                .companyName(customer.getCompanyName())
                .vatNumber(customer.getVatNumber())
                .fiscalCode(customer.getFiscalCode())
                .sdiCode(customer.getSdiCode())
                .pecEmail(customer.getPecEmail())
                .legalAddress(addressToAddressResponseDtoMapper.apply(customer.getLegalAddress()))
                .billingAddress(addressToAddressResponseDtoMapper.apply(customer.getBillingAddress()))
                .operationalAddresses(
                        customer.getOperationalAddresses().stream()
                                .map(addressToAddressResponseDtoMapper)
                                .collect(Collectors.toSet()))
                .contacts(
                        customer.getContacts().stream()
                                .map(customerContactToCustomerContactResponseDtoMapper)
                                .collect(Collectors.toSet()))
                .build();
    }
}

