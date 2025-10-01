package com.andrea.user.user.mapper;

import com.andrea.user.user.data.User;
import com.andrea.user.user.web.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToUserDtoMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return null;
    }

}
