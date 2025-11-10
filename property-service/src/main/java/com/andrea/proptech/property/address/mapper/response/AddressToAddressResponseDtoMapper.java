package com.andrea.proptech.property.address.mapper.response;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.web.dto.response.AddressResponseDto;
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
                .municipality(address.getMunicipality())
                .province(address.getProvince())
                .country(address.getCountry())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .notes(address.getNotes())
                .build();
    }
}