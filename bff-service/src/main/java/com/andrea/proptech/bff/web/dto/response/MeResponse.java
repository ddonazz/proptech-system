package com.andrea.proptech.bff.web.dto.response;

import java.util.Set;

public record MeResponse(
        String username,
        Set<String> authorities
) {
}
