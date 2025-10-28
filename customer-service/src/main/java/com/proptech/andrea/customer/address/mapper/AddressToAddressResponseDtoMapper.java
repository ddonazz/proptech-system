package com.proptech.andrea.customer.address.mapper;

import com.proptech.andrea.customer.address.data.Address;
import com.proptech.andrea.customer.address.web.dto.response.AddressResponseDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressToAddressResponseDtoMapper implements Function<Address, AddressResponseDto> {
    @Override
    public AddressResponseDto apply(Address address) {
        if (address == null) {
            return null;
        }
        
        return AddressResponseDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .province(address.getProvince())
                .country(address.getCountry())
                .build();
    }
}
