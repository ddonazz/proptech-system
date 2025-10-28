package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.customer.data.BusinessCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.BusinessCustomerUpdateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class BusinessCustomerUpdateDtoToBusinessCustomerMapper implements BiFunction<BusinessCustomerUpdateDto, BusinessCustomer, BusinessCustomer> {

    @Override
    public BusinessCustomer apply(@NonNull BusinessCustomerUpdateDto dto, @NonNull BusinessCustomer customer) {
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setCompanyName(dto.companyName());
        customer.setVatNumber(dto.vatNumber());
        customer.setFiscalCode(dto.fiscalCode());
        customer.setSdiCode(dto.sdiCode());
        customer.setPecEmail(dto.pecEmail());

        return customer;
    }
}
