package com.proptech.andrea.customer.customer.mapper.request.business;

import com.proptech.andrea.customer.address.mapper.AddressUpdateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class BusinessCustomerUpdateDtoToBusinessCustomerMapper implements BiFunction<BusinessCustomerUpdateDto, BusinessCustomer, BusinessCustomer> {

    private final AddressUpdateDtoToAddressMapper addressUpdateDtoToAddressMapper;

    @Override
    public BusinessCustomer apply(@NonNull BusinessCustomerUpdateDto dto, @NonNull BusinessCustomer customer) {
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setCompanyName(dto.companyName());
        customer.setVatNumber(dto.vatNumber());
        customer.setFiscalCode(dto.fiscalCode());
        customer.setSdiCode(dto.sdiCode());
        customer.setPecEmail(dto.pecEmail());

        customer.setLegalAddress(addressUpdateDtoToAddressMapper.apply(dto.legalAddress(), customer.getLegalAddress()));
        customer.setBillingAddress(addressUpdateDtoToAddressMapper.apply(dto.billingAddress(), customer.getBillingAddress()));

        return customer;
    }


}
