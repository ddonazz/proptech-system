package com.andrea.proptech.user.exception;

import com.andrea.proptech.core.exception.ErrorDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCodes implements ErrorDefinition {

    // --- User (1000 - 1099) ---
    USER_NOT_FOUND_BY_ID(1000, "user.not.found.id", "User not found"),
    USER_NOT_FOUND_BY_USERNAME(1001, "user.not.found.username", "User not found"),
    USER_INVALID_CREDENTIALS(1002, "user.invalid.credentials", "Invalid credentials"),

    // --- Role (1100 - 1199) ---
    ROLE_NOT_FOUND(1100, "role.not.found", "Role not found"),
    ROLE_IN_USE(1101, "role.in.use", "Role is currently in use"),

    // --- Permission (1200 - 1299) ---
    PERMISSION_NOT_FOUND(1200, "permission.not.found", "Permission not found");

    private final int code;
    private final String errorCode;
    private final String defaultMessage;
}
