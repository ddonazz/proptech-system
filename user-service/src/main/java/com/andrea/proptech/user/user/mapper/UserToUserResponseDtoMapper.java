package com.andrea.proptech.user.user.mapper;

import com.andrea.proptech.user.role.mapper.RoleToRoleResponseDtoMapper;
import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.web.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToUserResponseDtoMapper implements Function<User, UserResponseDto> {

    private final RoleToRoleResponseDtoMapper roleToRoleResponseDtoMapper;

    @Override
    public UserResponseDto apply(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles()
                        .stream()
                        .map(roleToRoleResponseDtoMapper)
                        .collect(Collectors.toSet()))
                .build();
    }

}
