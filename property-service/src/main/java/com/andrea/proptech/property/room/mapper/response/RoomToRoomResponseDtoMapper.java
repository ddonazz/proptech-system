package com.andrea.proptech.property.room.mapper.response;

import com.andrea.proptech.core.dto.TranslatedEnum;
import com.andrea.proptech.property.room.data.Room;
import com.andrea.proptech.property.room.data.RoomType;
import com.andrea.proptech.property.room.web.dto.response.RoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RoomToRoomResponseDtoMapper implements Function<Room, RoomResponseDto> {

    private final MessageSource messageSource;

    @Override
    public RoomResponseDto apply(@NonNull Room room) {
        Locale locale = LocaleContextHolder.getLocale();

        return RoomResponseDto.builder()
                .id(room.getId())
                .type(translateRoomType(room.getType(), locale))
                .areaMq(room.getAreaMq())
                .features(room.getFeatures())
                .build();
    }

    private TranslatedEnum translateRoomType(RoomType type, Locale locale) {
        if (type == null) {
            return null;
        }
        String code = "enum.roomtype." + type.name();
        String label = messageSource.getMessage(code, null, type.name(), locale);
        return new TranslatedEnum(type.name(), label);
    }
}
