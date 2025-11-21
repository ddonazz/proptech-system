package com.andrea.proptech.property.property.mapper.request;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.address.mapper.request.AddressCreateDtoToAddressMapper;
import com.andrea.proptech.property.address.web.dto.request.AddressCreateDto;
import com.andrea.proptech.property.property.data.BuildingAmenity;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyType;
import com.andrea.proptech.property.property.web.dto.request.PropertyCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyCreateDtoToPropertyMapperTest {

    @Mock
    private AddressCreateDtoToAddressMapper addressMapper;

    @InjectMocks
    private PropertyCreateDtoToPropertyMapper propertyMapper;

    @Test
    @DisplayName("Should map PropertyCreateDto to Property and delegate address mapping")
    void apply_shouldMapCorrectly() {
        // Given
        AddressCreateDto addressDto = new AddressCreateDto(
                "Via Milano", "1", "20100", "Milano", "Milano", "MI", "IT", null, null, null
        );

        PropertyCreateDto propertyDto = new PropertyCreateDto(
                "Residenza Sole",
                PropertyType.BUILDING,
                addressDto,
                2020,
                Set.of(BuildingAmenity.ELEVATOR, BuildingAmenity.GARAGE)
        );

        Address mockAddress = new Address(); // L'oggetto restituito dal mock
        when(addressMapper.apply(any(AddressCreateDto.class))).thenReturn(mockAddress);

        // When
        Property result = propertyMapper.apply(propertyDto);

        // Then
        assertNotNull(result);
        assertEquals(propertyDto.name(), result.getName());
        assertEquals(propertyDto.type(), result.getType());
        assertEquals(propertyDto.constructionYear(), result.getConstructionYear());

        // Verifica che gli amenities siano stati mappati
        assertEquals(2, result.getAmenities().size());
        assertTrue(result.getAmenities().contains(BuildingAmenity.ELEVATOR));

        // Verifica fondamentale: l'address mapper è stato chiamato?
        verify(addressMapper, times(1)).apply(addressDto);
        assertSame(mockAddress, result.getAddress());

        // Verifica che la lista units sia inizializzata vuota (come da logica del mapper)
        assertNotNull(result.getUnits());
        assertTrue(result.getUnits().isEmpty());
    }
}