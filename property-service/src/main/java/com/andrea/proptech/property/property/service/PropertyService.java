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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyCreateDtoToPropertyMapper propertyCreateDtoToPropertyMapper;
    private final PropertyUpdateDtoToPropertyMapper propertyUpdateDtoToPropertyMapper;
    private final PropertyToPropertyResponseDtoMapper propertyToPropertyResponseDtoMapper;

    @Transactional
    public PropertyResponseDto createProperty(PropertyCreateDto dto) {
        Property property = propertyCreateDtoToPropertyMapper.apply(dto);
        Property savedProperty = propertyRepository.save(property);
        return propertyToPropertyResponseDtoMapper.apply(savedProperty);
    }

    @Transactional
    public PropertyResponseDto updateProperty(Long id, PropertyUpdateDto dto) {
        Property property = retrieveProperty(id);
        Property propertyToUpdate = propertyUpdateDtoToPropertyMapper.apply(dto, property);
        Property updatedProperty = propertyRepository.save(propertyToUpdate);
        return propertyToPropertyResponseDtoMapper.apply(updatedProperty);
    }

    @Transactional(readOnly = true)
    public PropertyResponseDto getPropertyById(Long id) {
        Property property = retrieveProperty(id);
        return propertyToPropertyResponseDtoMapper.apply(property);
    }

    @Transactional
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException(PropertyErrorCodes.PROPERTY_NOT_FOUND, id);
        }

        propertyRepository.deleteById(id);
    }

    private Property retrieveProperty(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.PROPERTY_NOT_FOUND, id));
    }
}