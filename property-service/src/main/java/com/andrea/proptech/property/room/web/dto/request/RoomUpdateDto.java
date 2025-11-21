package com.andrea.proptech.property.room.web.dto.request;

import com.andrea.proptech.property.room.data.RoomType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record RoomUpdateDto(
        @NotNull
        RoomType type,

        @Positive
        Double areaMq,

        Set<String> features
) {
}