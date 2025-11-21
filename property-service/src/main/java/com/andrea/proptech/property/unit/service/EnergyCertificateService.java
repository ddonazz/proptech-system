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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnergyCertificateService {

    private final EnergyCertificateRepository certificateRepository;
    private final UnitRepository unitRepository;
    private final EnergyCertificateDtoToEnergyCertificateMapper createMapper;
    private final EnergyCertificateUpdateDtoToEnergyCertificateMapper updateMapper;
    private final EnergyCertificateToEnergyCertificateResponseDtoMapper responseMapper;

    @Transactional
    public EnergyCertificateResponseDto createEnergyCertificate(Long propertyId, Long unitId, EnergyCertificateDto dto) {
        Unit unit = retrieveUnit(propertyId, unitId);

        if (certificateRepository.existsByUnitId(unitId)) {
            throw new ResourceInUseException(PropertyErrorCodes.ENERGY_CERTIFICATE_EXISTS, unitId);
        }

        EnergyCertificate certificate = createMapper.apply(dto);
        certificate.setUnit(unit);

        EnergyCertificate savedCertificate = certificateRepository.save(certificate);
        return responseMapper.apply(savedCertificate);
    }

    @Transactional
    public EnergyCertificateResponseDto updateEnergyCertificate(Long propertyId, Long unitId, EnergyCertificateDto dto) {
        retrieveUnit(propertyId, unitId);
        EnergyCertificate certificate = retrieveCertificate(unitId);

        EnergyCertificate certificateToUpdate = updateMapper.apply(dto, certificate);
        EnergyCertificate updatedCertificate = certificateRepository.save(certificateToUpdate);
        return responseMapper.apply(updatedCertificate);
    }

    @Transactional(readOnly = true)
    public EnergyCertificateResponseDto getEnergyCertificate(Long propertyId, Long unitId) {
        retrieveUnit(propertyId, unitId);
        EnergyCertificate certificate = retrieveCertificate(unitId);
        return responseMapper.apply(certificate);
    }

    @Transactional
    public void deleteEnergyCertificate(Long propertyId, Long unitId) {
        retrieveUnit(propertyId, unitId);
        if (!certificateRepository.existsByUnitId(unitId)) {
            throw new ResourceNotFoundException(PropertyErrorCodes.ENERGY_CERTIFICATE_NOT_FOUND, unitId);
        }

        EnergyCertificate certificate = retrieveCertificate(unitId);
        certificateRepository.delete(certificate);
    }

    // --- Metodi helper ---

    private Unit retrieveUnit(Long propertyId, Long unitId) {
        return unitRepository.findByIdAndPropertyId(unitId, propertyId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.UNIT_NOT_FOUND, unitId));
    }

    private EnergyCertificate retrieveCertificate(Long unitId) {
        return certificateRepository.findByUnitId(unitId)
                .orElseThrow(() -> new ResourceNotFoundException(PropertyErrorCodes.ENERGY_CERTIFICATE_NOT_FOUND, unitId));
    }
}