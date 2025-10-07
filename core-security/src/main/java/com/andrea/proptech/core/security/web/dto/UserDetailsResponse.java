package com.andrea.proptech.core.security.web.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserDetailsResponse(Long id, String username, Set<String> roles) {
}
