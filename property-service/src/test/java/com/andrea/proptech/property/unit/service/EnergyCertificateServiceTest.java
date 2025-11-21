package com.andrea.proptech.property.unit.service;

import com.andrea.proptech.core.exception.ResourceInUseException;
import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.property.exception.PropertyErrorCodes;
import com.andrea.proptech.property.unit.data.EnergyCertificate;
import com.andrea.proptech.property.unit.data.EnergyCertificateRepository;
import com.andrea.proptech.property.unit.data.Unit;
import com.andrea.proptech.property.unit.data.UnitRepository;
import com.andrea.proptech.property.unit.mapper.request.EnergyCertificateDtoToEnergyCertificateMapper;
import com.andrea.proptech.property.unit.mapper.request.EnergyCertificateUpdateDtoToEnergyCertificateMapper;
import com.andrea.proptech.property.unit.mapper.response.EnergyCertificateToEnergyCertificateResponseDtoMapper;
import com.andrea.proptech.property.unit.web.dto.request.EnergyCertificateDto;
import com.andrea.proptech.property.unit.web.dto.response.EnergyCertificateResponseDto;
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
class EnergyCertificateServiceTest {

    @Mock
    private EnergyCertificateRepository certificateRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private EnergyCertificateDtoToEnergyCertificateMapper createMapper;
    @Mock
    private EnergyCertificateUpdateDtoToEnergyCertificateMapper updateMapper;
    @Mock
    private EnergyCertificateToEnergyCertificateResponseDtoMapper responseMapper;

    @InjectMocks
    private EnergyCertificateService service;

    @Test
    @DisplayName("createEnergyCertificate should throw ResourceInUseException if certificate already exists")
    void createEnergyCertificate_alreadyExists_throwsException() {
        // Given
        Long propId = 1L;
        Long unitId = 10L;

        when(unitRepository.findByIdAndPropertyId(unitId, propId)).thenReturn(Optional.of(new Unit()));
        // Simuliamo che esista già un certificato
        when(certificateRepository.existsByUnitId(unitId)).thenReturn(true);

        // When & Then
        ResourceInUseException ex = assertThrows(ResourceInUseException.class,
                () -> service.createEnergyCertificate(propId, unitId, mock(EnergyCertificateDto.class)));

        assertEquals(PropertyErrorCodes.ENERGY_CERTIFICATE_EXISTS.getErrorCode(), ex.getErrorCode());
        verify(certificateRepository, never()).save(any());
    }

    @Test
    @DisplayName("createEnergyCertificate should save if none exists")
    void createEnergyCertificate_success() {
        // Given
        Long propId = 1L;
        Long unitId = 10L;
        Unit unit = new Unit();
        EnergyCertificate cert = new EnergyCertificate();

        when(unitRepository.findByIdAndPropertyId(unitId, propId)).thenReturn(Optional.of(unit));
        when(certificateRepository.existsByUnitId(unitId)).thenReturn(false); // Non esiste ancora
        when(createMapper.apply(any())).thenReturn(cert);
        when(certificateRepository.save(cert)).thenReturn(cert);
        when(responseMapper.apply(cert)).thenReturn(mock(EnergyCertificateResponseDto.class));

        // When
        service.createEnergyCertificate(propId, unitId, mock(EnergyCertificateDto.class));

        // Then
        verify(certificateRepository).save(cert);
    }

    @Test
    @DisplayName("getEnergyCertificate should throw exception if not found")
    void getEnergyCertificate_notFound() {
        // Given
        Long propId = 1L;
        Long unitId = 10L;

        when(unitRepository.findByIdAndPropertyId(unitId, propId)).thenReturn(Optional.of(new Unit()));
        when(certificateRepository.findByUnitId(unitId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> service.getEnergyCertificate(propId, unitId));
    }
}