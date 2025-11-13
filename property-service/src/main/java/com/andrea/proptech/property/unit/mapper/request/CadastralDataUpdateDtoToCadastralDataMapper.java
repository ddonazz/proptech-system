package com.andrea.proptech.property.unit.mapper.request;

import com.andrea.proptech.property.unit.data.CadastralData;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataUpdateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class CadastralDataUpdateDtoToCadastralDataMapper implements BiFunction<CadastralDataUpdateDto, CadastralData, CadastralData> {

    @Override
    public CadastralData apply(@NonNull CadastralDataUpdateDto dto, @NonNull CadastralData data) {
        data.setSheet(dto.sheet());
        data.setParcel(dto.parcel());
        data.setSubordinate(dto.subordinate());
        data.setCategory(dto.category());
        data.setBuildingClass(dto.buildingClass());
        data.setConsistency(dto.consistency());
        data.setCadastralIncome(dto.cadastralIncome());
        return data;
    }
}