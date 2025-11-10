package com.andrea.proptech.property.property.mapper.response;

import com.andrea.proptech.core.dto.TranslatedEnum;
import com.andrea.proptech.property.address.mapper.response.AddressToAddressResponseDtoMapper;
import com.andrea.proptech.property.property.data.BuildingAmenity;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyType;
import com.andrea.proptech.property.property.web.dto.response.PropertyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyToPropertyResponseDtoMapper implements Function<Property, PropertyResponseDto> {

    private final MessageSource messageSource;
    private final AddressToAddressResponseDtoMapper addressToAddressResponseDtoMapper;

    @Override
    public PropertyResponseDto apply(Property property) {
        if (property == null) {
            return null;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return PropertyResponseDto.builder()
                .id(property.getId())
                .name(property.getName())
                .constructionYear(property.getConstructionYear())
                .address(addressToAddressResponseDtoMapper.apply(property.getAddress()))
                .type(translatePropertyType(property.getType(), locale))
                .amenities(
                        property.getAmenities().stream()
                                .map(amenity -> translateAmenity(amenity, locale))
                                .collect(Collectors.toSet())
                )
                .build();
    }

    private TranslatedEnum translatePropertyType(PropertyType type, Locale locale) {
        if (type == null) {
            return null;
        }
        String code = "enum.propertytype." + type.name();
        String label = messageSource.getMessage(code, null, type.name(), locale);
        return new TranslatedEnum(type.name(), label);
    }

    private TranslatedEnum translateAmenity(BuildingAmenity amenity, Locale locale) {
        if (amenity == null) {
            return null;
        }
        String code = "enum.buildingamenity." + amenity.name();
        String label = messageSource.getMessage(code, null, amenity.name(), locale);
        return new TranslatedEnum(amenity.name(), label);
    }
}