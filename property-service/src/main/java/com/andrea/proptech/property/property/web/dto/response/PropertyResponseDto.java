package com.andrea.proptech.property.property.web.dto.response;

import com.andrea.proptech.core.dto.TranslatedEnum;
import com.andrea.proptech.property.address.web.dto.response.AddressResponseDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record PropertyResponseDto(
        Long id,
        String name,
        Integer constructionYear,
        AddressResponseDto address,
        TranslatedEnum type,
        Set<TranslatedEnum> amenities
) {
}