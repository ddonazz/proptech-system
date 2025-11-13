package com.andrea.proptech.property.room.web.dto.response;

import com.andrea.proptech.core.dto.TranslatedEnum;
import lombok.Builder;

import java.util.Set;

@Builder
public record RoomResponseDto(
        Long id,
        TranslatedEnum type,
        Double areaMq,
        Set<String> features
) {
}
