package com.andrea.proptech.property.unit.mapper.request;

import com.andrea.proptech.property.unit.data.EnergyCertificate;
import com.andrea.proptech.property.unit.web.dto.request.EnergyCertificateDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EnergyCertificateDtoToEnergyCertificateMapper implements Function<EnergyCertificateDto, EnergyCertificate> {

    @Override
    public EnergyCertificate apply(EnergyCertificateDto dto) {
        if (dto == null) {
            return null;
        }
        EnergyCertificate certificate = new EnergyCertificate();
        certificate.setCertificateIdentifier(dto.certificateIdentifier());
        certificate.setEnergyClass(dto.energyClass());
        certificate.setGlobalPerformanceIndex(dto.globalPerformanceIndex());
        certificate.setIssueDate(dto.issueDate());
        certificate.setExpiryDate(dto.expiryDate());
        return certificate;
    }
}