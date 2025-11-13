package com.andrea.proptech.property.unit.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.exception.PropertyErrorCodes;
import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.property.data.PropertyRepository;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import com.andrea.proptech.property.unit.mapper.request.UnitCreateDtoToUnitMapper;
import com.andrea.proptech.property.unit.mapper.request.UnitUpdateDtoToUnitMapper;
import com.andrea.proptech.property.unit.mapper.response.UnitToUnitResponseDtoMapper;
import com.andrea.proptech.property.unit.web.dto.request.UnitCreateDto;
import com.andrea.proptech.property.unit.web.dto.request.UnitUpdateDto;
import com.andrea.proptech.property.unit.web.dto.response.UnitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final PropertyRepository propertyRepository;
    private final UnitCreateDtoToUnitMapper unitCreateDtoToUnitMapper;
    private final UnitUpdateDtoToUnitMapper unitUpdateDtoToUnitMapper;
    private final UnitToUnitResponseDtoMapper unitToUnitResponseDtoMapper;

    @Transactional
    public UnitResponseDto createUnit(Long propertyId, UnitCreateDto dto) {
        Property property = retrieveProperty(propertyId);

        // TODO: Recuperare l'ownerId dal token JWT o da un altro servizio

        Unit unit = unitCreateDtoToUnitMapper.apply(dto);
        unit.setProperty(property);

        Unit savedUnit = unitRepository.save(unit);
        return unitToUnitResponseDtoMapper.apply(savedUnit);
    }

    @Transactional
    public UnitResponseDto updateUnit(Long propertyId, Long unitId, UnitUpdateDto dto) {
        Unit unit = retrieveUnit(propertyId, unitId);
        Unit unitToUpdate = unitUpdateDtoToUnitMapper.apply(dto, unit);

        Unit updatedUnit = unitRepository.save(unitToUpdate);
        return unitToUnitResponseDtoMapper.apply(updatedUnit);
    }

    @Transactional(readOnly = true)
    public UnitResponseDto getUnitById(Long propertyId, Long unitId) {
        Unit unit = retrieveUnit(propertyId, unitId);
        return unitToUnitResponseDtoMapper.apply(unit);
    }

    @Transactional(readOnly = true)
    public Page<UnitResponseDto> getAllUnitsForProperty(Long propertyId, Pageable pageable) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException(PropertyErrorCodes.PROPERTY_NOT_FOUND, propertyId);
        }

        return unitRepository.findByPropertyId(propertyId, pageable)
                .map(unitToUnitResponseDtoMapper);
    }

    @Transactional
    public void deleteUnit(Long propertyId, Long unitId) {
        Unit unit = retrieveUnit(propertyId, unitId);
        unitRepository.delete(unit);
    }
    
    private Property retrieveProperty(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.PROPERTY_NOT_FOUND, id));
    }

    private Unit retrieveUnit(Long propertyId, Long unitId) {
        return unitRepository.findByIdAndPropertyId(unitId, propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.UNIT_NOT_FOUND, unitId));
    }
}