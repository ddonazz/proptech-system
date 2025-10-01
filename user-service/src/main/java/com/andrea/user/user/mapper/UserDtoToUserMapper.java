package com.andrea.user.user.mapper;

import com.andrea.user.user.data.User;
import com.andrea.user.user.web.dto.UserDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoToUserMapper implements Function<UserDto, User> {
    @Override
    public User apply(@NonNull UserDto userDto) {
        return new User(userDto.username());
    }
}
