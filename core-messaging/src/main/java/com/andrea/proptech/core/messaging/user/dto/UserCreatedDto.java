package com.andrea.proptech.core.messaging.user.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserCreatedDto(String firstName, String lastName, String email) implements Serializable {
}
