package com.andrea.proptech.property.unit.mapper.response;

import com.andrea.proptech.property.unit.data.EnergyCertificate;
import com.andrea.proptech.property.unit.web.dto.response.EnergyCertificateResponseDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EnergyCertificateToEnergyCertificateResponseDtoMapper implements Function<EnergyCertificate, EnergyCertificateResponseDto> {

    @Override
    public EnergyCertificateResponseDto apply(EnergyCertificate certificate) {
        if (certificate == null) {
            return null;
        }
        return EnergyCertificateResponseDto.builder()
                .id(certificate.getId())
                .certificateIdentifier(certificate.getCertificateIdentifier())
                .energyClass(certificate.getEnergyClass())
                .globalPerformanceIndex(certificate.getGlobalPerformanceIndex())
                .issueDate(certificate.getIssueDate())
                .expiryDate(certificate.getExpiryDate())
                .build();
    }
}