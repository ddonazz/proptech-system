package com.andrea.proptech.property.address.mapper.request;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.web.dto.request.AddressCreateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressCreateDtoToAddressMapper implements Function<AddressCreateDto, Address> {

    @Override
    public Address apply(@NonNull AddressCreateDto dto) {
        Address address = new Address();

        address.setStreet(dto.street());
        address.setNumber(dto.number());
        address.setPostalCode(dto.postalCode());
        address.setCity(dto.city());
        address.setMunicipality(dto.municipality());
        address.setProvince(dto.province());
        address.setCountry(dto.country());
        address.setLatitude(dto.latitude());
        address.setLongitude(dto.longitude());
        address.setNotes(dto.notes());

        return address;
    }
}
