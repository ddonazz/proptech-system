package com.andrea.proptech.user.user.mapper;

import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.web.dto.request.UserCreateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserCreateDtoToUserMapper implements Function<UserCreateDto, User> {
    @Override
    public User apply(@NonNull UserCreateDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());

        return user;
    }
}
