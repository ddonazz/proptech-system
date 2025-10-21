package com.andrea.proptech.user.user.mapper;

import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.web.dto.UserDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoToUserMapper implements Function<UserDto, User> {
    @Override
    public User apply(@NonNull UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(user.getEmail());
        
        return user;
    }
}
