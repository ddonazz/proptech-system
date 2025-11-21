package com.andrea.proptech.property.address.mapper.request;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.web.dto.request.AddressCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressCreateDtoToAddressMapperTest {

    private final AddressCreateDtoToAddressMapper mapper = new AddressCreateDtoToAddressMapper();

    @Test
    @DisplayName("Should map AddressCreateDto to Address entity correctly")
    void apply_shouldMapFieldsCorrectly() {
        // Given
        AddressCreateDto dto = new AddressCreateDto(
                "Via Roma", "10", "00100", "Roma", "Roma", "RM", "IT",
                41.9028, 12.4964, "Near Colosseum"
        );

        // When
        Address result = mapper.apply(dto);

        // Then
        assertNotNull(result);
        assertEquals(dto.street(), result.getStreet());
        assertEquals(dto.number(), result.getNumber());
        assertEquals(dto.postalCode(), result.getPostalCode());
        assertEquals(dto.city(), result.getCity());
        assertEquals(dto.municipality(), result.getMunicipality());
        assertEquals(dto.province(), result.getProvince());
        assertEquals(dto.country(), result.getCountry());
        assertEquals(dto.latitude(), result.getLatitude());
        assertEquals(dto.longitude(), result.getLongitude());
        assertEquals(dto.notes(), result.getNotes());
    }

    @Test
    @DisplayName("Should throw NullPointerException when input is null")
    void apply_shouldThrowException_whenInputIsNull() {
        assertThrows(NullPointerException.class, () -> mapper.apply(null));
    }
}