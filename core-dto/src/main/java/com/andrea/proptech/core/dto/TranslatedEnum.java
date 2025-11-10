package com.andrea.proptech.core.dto;

import lombok.Builder;

@Builder
public record TranslatedEnum(
        String code,
        String label
) {
}
