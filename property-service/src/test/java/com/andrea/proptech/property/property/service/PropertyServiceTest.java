package com.andrea.proptech.property.property.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.exception.PropertyErrorCodes;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyRepository;
import com.andrea.proptech.property.property.mapper.request.PropertyCreateDtoToPropertyMapper;
import com.andrea.proptech.property.property.mapper.request.PropertyUpdateDtoToPropertyMapper;
import com.andrea.proptech.property.property.mapper.response.PropertyToPropertyResponseDtoMapper;
import com.andrea.proptech.property.property.web.dto.request.PropertyCreateDto;
import com.andrea.proptech.property.property.web.dto.request.PropertyUpdateDto;
import com.andrea.proptech.property.property.web.dto.response.PropertyResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private PropertyCreateDtoToPropertyMapper createMapper;
    @Mock
    private PropertyUpdateDtoToPropertyMapper updateMapper;
    @Mock
    private PropertyToPropertyResponseDtoMapper responseMapper;

    @InjectMocks
    private PropertyService propertyService;

    @Test
    @DisplayName("createProperty should save and return mapped response")
    void createProperty_success() {
        // Given
        PropertyCreateDto request = mock(PropertyCreateDto.class);
        Property propertyEntity = new Property();
        Property savedProperty = new Property();
        savedProperty.setId(1L);
        PropertyResponseDto expectedResponse = PropertyResponseDto.builder().id(1L).build();

        when(createMapper.apply(request)).thenReturn(propertyEntity);
        when(propertyRepository.save(propertyEntity)).thenReturn(savedProperty);
        when(responseMapper.apply(savedProperty)).thenReturn(expectedResponse);

        // When
        PropertyResponseDto result = propertyService.createProperty(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(propertyRepository).save(propertyEntity);
    }

    @Test
    @DisplayName("getPropertyById should return response when found")
    void getPropertyById_found() {
        // Given
        Long id = 1L;
        Property property = new Property();
        PropertyResponseDto expectedResponse = PropertyResponseDto.builder().id(id).build();

        when(propertyRepository.findById(id)).thenReturn(Optional.of(property));
        when(responseMapper.apply(property)).thenReturn(expectedResponse);

        // When
        PropertyResponseDto result = propertyService.getPropertyById(id);

        // Then
        assertEquals(id, result.id());
    }

    @Test
    @DisplayName("getPropertyById should throw ResourceNotFoundException when not found")
    void getPropertyById_notFound() {
        // Given
        Long id = 99L;
        when(propertyRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> propertyService.getPropertyById(id));

        assertEquals(PropertyErrorCodes.PROPERTY_NOT_FOUND.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @DisplayName("updateProperty should update entity and return new response")
    void updateProperty_success() {
        // Given
        Long id = 1L;
        PropertyUpdateDto updateRequest = mock(PropertyUpdateDto.class);
        Property existingProperty = new Property();
        Property updatedEntity = new Property();
        PropertyResponseDto expectedResponse = PropertyResponseDto.builder().id(id).name("Updated").build();

        when(propertyRepository.findById(id)).thenReturn(Optional.of(existingProperty));
        when(updateMapper.apply(updateRequest, existingProperty)).thenReturn(updatedEntity);
        when(propertyRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(responseMapper.apply(updatedEntity)).thenReturn(expectedResponse);

        // When
        PropertyResponseDto result = propertyService.updateProperty(id, updateRequest);

        // Then
        assertEquals("Updated", result.name());
        verify(propertyRepository).save(updatedEntity);
    }

    @Test
    @DisplayName("deleteProperty should delete entity when exists")
    void deleteProperty_success() {
        // Given
        Long id = 1L;
        when(propertyRepository.existsById(id)).thenReturn(true);

        // When
        propertyService.deleteProperty(id);

        // Then
        verify(propertyRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteProperty should throw exception when not exists")
    void deleteProperty_notFound() {
        // Given
        Long id = 99L;
        when(propertyRepository.existsById(id)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> propertyService.deleteProperty(id));
        verify(propertyRepository, never()).deleteById(any());
    }
}