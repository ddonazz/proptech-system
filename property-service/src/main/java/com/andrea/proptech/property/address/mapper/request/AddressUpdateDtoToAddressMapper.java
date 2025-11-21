package com.andrea.proptech.property.address.mapper.request;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.web.dto.request.AddressUpdateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AddressUpdateDtoToAddressMapper implements BiFunction<AddressUpdateDto, Address, Address> {

    @Override
    public Address apply(@NonNull AddressUpdateDto dto, @NonNull Address address) {

        // Applica i valori del DTO all'entità esistente
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