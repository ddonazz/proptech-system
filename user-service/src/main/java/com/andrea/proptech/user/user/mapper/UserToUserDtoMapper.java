package com.andrea.proptech.user.user.mapper;

import com.andrea.proptech.user.role.mapper.RoleToRoleDtoMapper;
import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToUserDtoMapper implements Function<User, UserDto> {

    private final RoleToRoleDtoMapper roleToRoleDtoMapper;

    @Override
    public UserDto apply(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles()
                        .stream()
                        .map(roleToRoleDtoMapper)
                        .collect(Collectors.toSet()))
                .build();
    }

}
