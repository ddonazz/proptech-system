package com.andrea.proptech.property.room.mapper.request;

import com.andrea.proptech.property.room.data.Room;
import com.andrea.proptech.property.room.web.dto.request.RoomUpdateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.BiFunction;

@Component
public class RoomUpdateDtoToRoomMapper implements BiFunction<RoomUpdateDto, Room, Room> {

    @Override
    public Room apply(@NonNull RoomUpdateDto dto, @NonNull Room room) {
        room.setType(dto.type());
        room.setAreaMq(dto.areaMq());
        room.setFeatures(dto.features() != null ? dto.features() : Collections.emptySet());
        return room;
    }
}