package com.andrea.proptech.property.unit.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyRepository;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import com.andrea.proptech.property.unit.mapper.request.UnitCreateDtoToUnitMapper;
import com.andrea.proptech.property.unit.mapper.response.UnitToUnitResponseDtoMapper;
import com.andrea.proptech.property.unit.web.dto.request.UnitCreateDto;
import com.andrea.proptech.property.unit.web.dto.response.UnitResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    private UnitRepository unitRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UnitCreateDtoToUnitMapper createMapper;
    @Mock
    private UnitToUnitResponseDtoMapper responseMapper;

    @InjectMocks
    private UnitService unitService;

    @Test
    @DisplayName("createUnit should link unit to property and save")
    void createUnit_success() {
        // Given
        Long propertyId = 100L;
        UnitCreateDto request = mock(UnitCreateDto.class);
        Property property = new Property();
        property.setId(propertyId);

        Unit unitToSave = new Unit();
        Unit savedUnit = new Unit();
        savedUnit.setId(1L);

        UnitResponseDto expectedResponse = UnitResponseDto.builder().id(1L).build();

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(createMapper.apply(request)).thenReturn(unitToSave);
        when(unitRepository.save(unitToSave)).thenReturn(savedUnit);
        when(responseMapper.apply(savedUnit)).thenReturn(expectedResponse);

        // When
        UnitResponseDto result = unitService.createUnit(propertyId, request);

        // Then
        assertNotNull(result);
        assertEquals(property, unitToSave.getProperty());
        verify(unitRepository).save(unitToSave);
    }

    @Test
    @DisplayName("createUnit should throw exception if property does not exist")
    void createUnit_propertyNotFound() {
        // Given
        Long propertyId = 999L;
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> unitService.createUnit(propertyId, mock(UnitCreateDto.class)));

        verify(unitRepository, never()).save(any());
    }

    @Test
    @DisplayName("getAllUnitsForProperty should check property existence first")
    void getAllUnitsForProperty_success() {
        // Given
        Long propertyId = 100L;
        Pageable pageable = Pageable.unpaged();
        Unit unit = new Unit();
        Page<Unit> unitPage = new PageImpl<>(List.of(unit));

        when(propertyRepository.existsById(propertyId)).thenReturn(true);
        when(unitRepository.findByPropertyId(propertyId, pageable)).thenReturn(unitPage);
        when(responseMapper.apply(unit)).thenReturn(mock(UnitResponseDto.class));

        // When
        Page<UnitResponseDto> result = unitService.getAllUnitsForProperty(propertyId, pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        verify(propertyRepository).existsById(propertyId);
    }

    @Test
    @DisplayName("getAllUnitsForProperty should fail if property not exists")
    void getAllUnitsForProperty_propertyNotFound() {
        // Given
        Long propertyId = 999L;
        when(propertyRepository.existsById(propertyId)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> unitService.getAllUnitsForProperty(propertyId, Pageable.unpaged()));
    }
}