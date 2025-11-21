package com.andrea.proptech.property.property.mapper.response;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.mapper.response.AddressToAddressResponseDtoMapper;
import com.andrea.proptech.property.address.web.dto.response.AddressResponseDto;
import com.andrea.proptech.property.property.data.BuildingAmenity;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyType;
import com.andrea.proptech.property.property.web.dto.response.PropertyResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyToPropertyResponseDtoMapperTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private AddressToAddressResponseDtoMapper addressMapper;

    @InjectMocks
    private PropertyToPropertyResponseDtoMapper propertyMapper;

    @Test
    @DisplayName("Should map Property to ResponseDto with translated enums")
    void apply_shouldMapWithTranslations() {
        // Given
        Property property = new Property();
        property.setId(1L);
        property.setName("Villa Vista");
        property.setType(PropertyType.VILLA);
        property.setConstructionYear(2015);
        property.setAmenities(Set.of(BuildingAmenity.POOL));
        property.setAddress(new Address());

        AddressResponseDto mockAddressDto = AddressResponseDto.builder().city("Napoli").build();

        // Mock delle dipendenze
        when(addressMapper.apply(any(Address.class))).thenReturn(mockAddressDto);

        // Mock delle traduzioni (Importante: LocaleContextHolder usa il default del sistema nei test se non settato)
        Locale locale = LocaleContextHolder.getLocale();
        when(messageSource.getMessage(eq("enum.propertytype.VILLA"), any(), eq("VILLA"), eq(locale)))
                .thenReturn("Villa Singola");
        when(messageSource.getMessage(eq("enum.buildingamenity.POOL"), any(), eq("POOL"), eq(locale)))
                .thenReturn("Piscina");

        // When
        PropertyResponseDto result = propertyMapper.apply(property);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Villa Vista", result.name());

        // Verifica traduzione Type
        assertNotNull(result.type());
        assertEquals("VILLA", result.type().code());
        assertEquals("Villa Singola", result.type().label());

        // Verifica traduzione Amenities
        assertEquals(1, result.amenities().size());
        var amenity = result.amenities().iterator().next();
        assertEquals("POOL", amenity.code());
        assertEquals("Piscina", amenity.label());

        // Verifica Address
        assertEquals("Napoli", result.address().city());
    }

    @Test
    @DisplayName("Should return null if property is null")
    void apply_shouldReturnNull_whenInputIsNull() {
        assertNull(propertyMapper.apply(null));
    }
}