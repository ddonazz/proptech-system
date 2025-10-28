package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.address.mapper.AddressCreateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.PrivateCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.PrivateCustomerCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PrivateCustomerCreateDtoToPrivateCustomerMapper implements Function<PrivateCustomerCreateDto, PrivateCustomer> {

    private final AddressCreateDtoToAddressMapper addressCreateDtoToAddressMapper;

    @Override
    public PrivateCustomer apply(@NonNull PrivateCustomerCreateDto dto) {
        PrivateCustomer customer = new PrivateCustomer();

        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setUserId(dto.userId());

        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setFiscalCode(dto.fiscalCode());
        customer.setBirthDate(dto.birthDate());
        customer.setBirthPlace(dto.birthPlace());
        customer.setNationality(dto.nationality());

        if (dto.billingAddress() != null) {
            customer.setBillingAddress(addressCreateDtoToAddressMapper.apply(dto.billingAddress()));
        }

        return customer;
    }

}
