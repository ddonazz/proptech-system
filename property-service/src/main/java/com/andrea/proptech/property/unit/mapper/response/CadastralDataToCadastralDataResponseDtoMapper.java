package com.andrea.proptech.property.unit.mapper.response;

import com.andrea.proptech.property.unit.data.CadastralData;
import com.andrea.proptech.property.unit.web.dto.response.CadastralDataResponseDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CadastralDataToCadastralDataResponseDtoMapper implements Function<CadastralData, CadastralDataResponseDto> {

    @Override
    public CadastralDataResponseDto apply(@NonNull CadastralData data) {
        return CadastralDataResponseDto.builder()
                .id(data.getId())
                .sheet(data.getSheet())
                .parcel(data.getParcel())
                .subordinate(data.getSubordinate())
                .category(data.getCategory())
                .buildingClass(data.getBuildingClass())
                .consistency(data.getConsistency())
                .cadastralIncome(data.getCadastralIncome())
                .build();
    }
}