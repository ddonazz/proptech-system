package com.proptech.andrea.customer.customer.mapper.request.business;

import com.proptech.andrea.customer.address.mapper.AddressCreateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BusinessCustomerCreateDtoToBusinessCustomerMapper implements Function<BusinessCustomerCreateDto, BusinessCustomer> {

    private final AddressCreateDtoToAddressMapper addressCreateDtoToAddressMapper;

    @Override
    public BusinessCustomer apply(BusinessCustomerCreateDto dto) {
        BusinessCustomer customer = new BusinessCustomer();

        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());

        customer.setCompanyName(dto.companyName());
        customer.setVatNumber(dto.vatNumber());
        customer.setFiscalCode(dto.fiscalCode());
        customer.setSdiCode(dto.sdiCode());
        customer.setPecEmail(dto.pecEmail());

        customer.setLegalAddress(addressCreateDtoToAddressMapper.apply(dto.legalAddress()));
        customer.setBillingAddress(addressCreateDtoToAddressMapper.apply(dto.billingAddress()));

        customer.setOperationalAddresses(Collections.emptySet());
        customer.setContacts(Collections.emptySet());

        return customer;
    }
}
