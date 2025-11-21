package com.andrea.proptech.property.property.mapper.request;

import com.andrea.proptech.property.address.mapper.request.AddressUpdateDtoToAddressMapper;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.web.dto.request.PropertyUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class PropertyUpdateDtoToPropertyMapper implements BiFunction<PropertyUpdateDto, Property, Property> {

    private final AddressUpdateDtoToAddressMapper addressUpdateDtoToAddressMapper;

    @Override
    public Property apply(@NonNull PropertyUpdateDto dto, @NonNull Property property) {

        property.setName(dto.name());
        property.setType(dto.type());
        property.setConstructionYear(dto.constructionYear());

        addressUpdateDtoToAddressMapper.apply(dto.address(), property.getAddress());

        property.setAmenities(dto.amenities() != null ? dto.amenities() : Collections.emptySet());

        return property;
    }
}