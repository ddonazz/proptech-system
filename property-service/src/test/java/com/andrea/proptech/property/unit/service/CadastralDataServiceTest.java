package com.andrea.proptech.property.unit.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.unit.data.CadastralData;
import com.andrea.proptech.property.unit.data.CadastralDataRepository;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import com.andrea.proptech.property.unit.mapper.request.CadastralDataCreateDtoToCadastralDataMapper;
import com.andrea.proptech.property.unit.mapper.request.CadastralDataUpdateDtoToCadastralDataMapper;
import com.andrea.proptech.property.unit.mapper.response.CadastralDataToCadastralDataResponseDtoMapper;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataCreateDto;
import com.andrea.proptech.property.unit.web.dto.response.CadastralDataResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastralDataServiceTest {

    @Mock
    private CadastralDataRepository cadastralDataRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private CadastralDataCreateDtoToCadastralDataMapper createMapper;
    @Mock
    private CadastralDataUpdateDtoToCadastralDataMapper updateMapper;
    @Mock
    private CadastralDataToCadastralDataResponseDtoMapper responseMapper;

    @InjectMocks
    private CadastralDataService service;

    @Test
    @DisplayName("createCadastralData should throw exception if Unit not found")
    void createCadastralData_unitNotFound() {
        Long propId = 1L;
        Long unitId = 999L;
        when(unitRepository.findByIdAndPropertyId(unitId, propId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.createCadastralData(propId, unitId, mock(CadastralDataCreateDto.class)));
    }

    @Test
    @DisplayName("createCadastralData should save data linked to unit")
    void createCadastralData_success() {
        Long propId = 1L;
        Long unitId = 10L;
        Unit unit = new Unit();
        CadastralData data = new CadastralData();

        when(unitRepository.findByIdAndPropertyId(unitId, propId)).thenReturn(Optional.of(unit));
        when(createMapper.apply(any())).thenReturn(data);
        when(cadastralDataRepository.save(data)).thenReturn(data);
        when(responseMapper.apply(data)).thenReturn(mock(CadastralDataResponseDto.class));

        service.createCadastralData(propId, unitId, mock(CadastralDataCreateDto.class));

        verify(cadastralDataRepository).save(data);
        assertEquals(unit, data.getUnit());
    }

    @Test
    @DisplayName("deleteCadastralData should delete if found")
    void deleteCadastralData_success() {
        Long propId = 1L;
        Long unitId = 10L;
        Long dataId = 100L;
        CadastralData data = new CadastralData();

        when(unitRepository.findByIdAndPropertyId(unitId, propId)).thenReturn(Optional.of(new Unit()));
        when(cadastralDataRepository.findByIdAndUnitId(dataId, unitId)).thenReturn(Optional.of(data));

        service.deleteCadastralData(propId, unitId, dataId);

        verify(cadastralDataRepository).delete(data);
    }
}