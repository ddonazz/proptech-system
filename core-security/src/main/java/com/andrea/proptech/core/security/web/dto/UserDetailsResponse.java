package com.andrea.proptech.core.security.web.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserDetailsResponse(String username, String password, Set<String> scopes) {
}
