package com.andrea.proptech.property.unit.mapper.request;

import com.andrea.proptech.property.unit.data.EnergyCertificate;
import com.andrea.proptech.property.unit.web.dto.request.EnergyCertificateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class EnergyCertificateUpdateDtoToEnergyCertificateMapper implements BiFunction<EnergyCertificateDto, EnergyCertificate, EnergyCertificate> {

    @Override
    public EnergyCertificate apply(@NonNull EnergyCertificateDto dto, @NonNull EnergyCertificate certificate) {
        certificate.setCertificateIdentifier(dto.certificateIdentifier());
        certificate.setEnergyClass(dto.energyClass());
        certificate.setGlobalPerformanceIndex(dto.globalPerformanceIndex());
        certificate.setIssueDate(dto.issueDate());
        certificate.setExpiryDate(dto.expiryDate());
        return certificate;
    }
}