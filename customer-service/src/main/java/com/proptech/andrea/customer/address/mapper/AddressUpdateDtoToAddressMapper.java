package com.proptech.andrea.customer.address.mapper;

import com.proptech.andrea.customer.address.data.Address;
import com.proptech.andrea.customer.address.web.dto.request.AddressUpdateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AddressUpdateDtoToAddressMapper implements BiFunction<AddressUpdateDto, Address, Address> {

    @Override
    public Address apply(@NonNull AddressUpdateDto dto, @NonNull Address address) {
        address.setStreet(dto.street());
        address.setNumber(dto.number());
        address.setPostalCode(dto.postalCode());
        address.setCity(dto.city());
        address.setProvince(dto.province());
        address.setCountry(dto.country());

        return address;
    }
}
