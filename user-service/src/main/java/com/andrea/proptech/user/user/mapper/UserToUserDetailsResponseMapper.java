package com.andrea.proptech.user.user.mapper;

import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import com.andrea.proptech.user.user.data.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserToUserDetailsResponseMapper implements Function<User, UserDetailsResponse> {
    @Override
    public UserDetailsResponse apply(User user) {
        return UserDetailsResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles().stream()
                        .map(String::valueOf)
                        .collect(Collectors.toSet()))
                .build();
    }
}
