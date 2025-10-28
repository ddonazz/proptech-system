package com.proptech.andrea.customer.address.mapper;

import com.proptech.andrea.customer.address.data.Address;
import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressCreateDtoToAddressMapper implements Function<AddressCreateDto, Address> {

    @Override
    public Address apply(@NonNull AddressCreateDto dto) {
        return Address.builder()
                .street(dto.street())
                .number(dto.number())
                .postalCode(dto.postalCode())
                .city(dto.city())
                .province(dto.province())
                .country(dto.country())
                .build();
    }

}
