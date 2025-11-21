package com.andrea.proptech.property.room.mapper.request;

import com.andrea.proptech.property.room.data.Room;
import com.andrea.proptech.property.room.web.dto.request.RoomCreateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;

@Component
public class RoomCreateDtoToRoomMapper implements Function<RoomCreateDto, Room> {

    @Override
    public Room apply(@NonNull RoomCreateDto dto) {
        Room room = new Room();
        room.setType(dto.type());
        room.setAreaMq(dto.areaMq());
        room.setFeatures(dto.features() != null ? dto.features() : Collections.emptySet());
        return room;
    }
}
