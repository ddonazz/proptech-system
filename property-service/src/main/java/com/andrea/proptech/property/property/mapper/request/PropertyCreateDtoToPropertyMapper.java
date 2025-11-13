package com.andrea.proptech.property.property.mapper.request;

import com.andrea.proptech.property.address.mapper.request.AddressCreateDtoToAddressMapper;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.web.dto.request.PropertyCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PropertyCreateDtoToPropertyMapper implements Function<PropertyCreateDto, Property> {

    private final AddressCreateDtoToAddressMapper addressCreateDtoToAddressMapper;

    @Override
    public Property apply(@NonNull PropertyCreateDto dto) {
        Property property = new Property();

        property.setName(dto.name());
        property.setType(dto.type());
        property.setConstructionYear(dto.constructionYear());

        property.setAddress(addressCreateDtoToAddressMapper.apply(dto.address()));

        property.setAmenities(dto.amenities() != null ? dto.amenities() : Collections.emptySet());
        property.setUnits(Collections.emptySet());

        return property;
    }
}