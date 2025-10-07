package com.andrea.proptech.user.exception;

import java.time.Instant;

public record ErrorResponse(
        Instant instant,
        int status,
        String error,
        String message,
        String path
) {
}
