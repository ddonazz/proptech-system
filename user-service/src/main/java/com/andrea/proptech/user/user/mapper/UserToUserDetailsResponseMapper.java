package com.andrea.proptech.user.user.mapper;

import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.user.data.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserToUserDetailsResponseMapper implements Function<User, UserDetailsResponse> {
    @Override
    public UserDetailsResponse apply(User user) {
        return UserDetailsResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .scopes(user.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                        .map(Permission::getAuthority)
                        .collect(Collectors.toSet()))
                .build();
    }
}
