package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.address.mapper.AddressCreateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.BusinessCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.BusinessCustomerCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        if (dto.legalAddress() != null) {
            customer.setLegalAddress(addressCreateDtoToAddressMapper.apply(dto.legalAddress()));
        }

        if (dto.billingAddress() != null) {
            customer.setBillingAddress(addressCreateDtoToAddressMapper.apply(dto.billingAddress()));
        }

        if (dto.operationalAddresses() != null) {
            customer.setOperationalAddresses(
                    dto.operationalAddresses().stream()
                            .map(addressCreateDtoToAddressMapper)
                            .collect(Collectors.toSet())
            );
        } else {
            customer.setOperationalAddresses(Collections.emptySet());
        }

        customer.setContacts(Collections.emptySet());

        return customer;
    }
}
