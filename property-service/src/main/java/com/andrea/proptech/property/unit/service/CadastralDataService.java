package com.andrea.proptech.property.unit.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.exception.PropertyErrorCodes;
import com.andrea.proptech.property.unit.data.CadastralData;
import com.andrea.proptech.property.unit.data.CadastralDataRepository;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import com.andrea.proptech.property.unit.mapper.request.CadastralDataCreateDtoToCadastralDataMapper;
import com.andrea.proptech.property.unit.mapper.request.CadastralDataUpdateDtoToCadastralDataMapper;
import com.andrea.proptech.property.unit.mapper.response.CadastralDataToCadastralDataResponseDtoMapper;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataCreateDto;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataUpdateDto;
import com.andrea.proptech.property.unit.web.dto.response.CadastralDataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CadastralDataService {

    private final CadastralDataRepository cadastralDataRepository;
    private final UnitRepository unitRepository;
    private final CadastralDataCreateDtoToCadastralDataMapper createMapper;
    private final CadastralDataUpdateDtoToCadastralDataMapper updateMapper;
    private final CadastralDataToCadastralDataResponseDtoMapper responseMapper;

    @Transactional
    public CadastralDataResponseDto createCadastralData(Long propertyId, Long unitId, CadastralDataCreateDto dto) {
        Unit unit = retrieveUnit(propertyId, unitId);
        CadastralData data = createMapper.apply(dto);
        data.setUnit(unit);

        CadastralData savedData = cadastralDataRepository.save(data);
        return responseMapper.apply(savedData);
    }

    @Transactional
    public CadastralDataResponseDto updateCadastralData(Long propertyId, Long unitId, Long dataId, CadastralDataUpdateDto dto) {
        retrieveUnit(propertyId, unitId);
        CadastralData data = retrieveCadastralData(unitId, dataId);

        CadastralData dataToUpdate = updateMapper.apply(dto, data);
        CadastralData updatedData = cadastralDataRepository.save(dataToUpdate);
        return responseMapper.apply(updatedData);
    }

    @Transactional(readOnly = true)
    public CadastralDataResponseDto getCadastralDataById(Long propertyId, Long unitId, Long dataId) {
        retrieveUnit(propertyId, unitId);
        CadastralData data = retrieveCadastralData(unitId, dataId);
        return responseMapper.apply(data);
    }

    @Transactional(readOnly = true)
    public List<CadastralDataResponseDto> getAllCadastralDataForUnit(Long propertyId, Long unitId) {
        retrieveUnit(propertyId, unitId);
        return cadastralDataRepository.findByUnitId(unitId).stream()
                .map(responseMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCadastralData(Long propertyId, Long unitId, Long dataId) {
        retrieveUnit(propertyId, unitId);
        CadastralData data = retrieveCadastralData(unitId, dataId);
        cadastralDataRepository.delete(data);
    }

    // --- Metodi helper ---

    private Unit retrieveUnit(Long propertyId, Long unitId) {
        return unitRepository.findByIdAndPropertyId(unitId, propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.UNIT_NOT_FOUND, unitId));
    }

    private CadastralData retrieveCadastralData(Long unitId, Long dataId) {
        return cadastralDataRepository.findByIdAndUnitId(dataId, unitId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.CADASTRAL_DATA_NOT_FOUND, dataId, unitId));
    }
}