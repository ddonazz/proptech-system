package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.customer.data.PrivateCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.PrivateCustomerUpdateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class PrivateCustomerUpdateDtoToPrivateCustomerMapper implements BiFunction<PrivateCustomerUpdateDto, PrivateCustomer, PrivateCustomer> {

    @Override
    public PrivateCustomer apply(@NonNull PrivateCustomerUpdateDto dto, @NonNull PrivateCustomer customer) {
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setBirthDate(dto.birthDate());
        customer.setBirthPlace(dto.birthPlace());
        customer.setNationality(dto.nationality());

        return customer;
    }
}
