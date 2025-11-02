package com.andrea.proptech.auth.service;

import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final WebClient userWebClient;

    public CustomUserDetailsService(@Qualifier("user-service-web-client") WebClient userWebClient) {
        this.userWebClient = userWebClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsResponse userDetailsResponse = userWebClient.get()
                .uri("/internal/v1/users/by-username/{username}", username)
                .retrieve()
                .bodyToMono(UserDetailsResponse.class)
                .blockOptional()
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

        return User.builder()
                .username(userDetailsResponse.username())
                .password(userDetailsResponse.password())
                .authorities(userDetailsResponse.scopes().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()))
                .build();
    }

}
