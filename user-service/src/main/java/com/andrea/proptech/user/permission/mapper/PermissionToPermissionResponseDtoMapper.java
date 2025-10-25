package com.andrea.proptech.user.permission.mapper;

import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.web.dto.response.PermissionResponseDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PermissionToPermissionResponseDtoMapper implements Function<Permission, PermissionResponseDto> {

    @Override
    public PermissionResponseDto apply(@NonNull Permission permission) {
        return PermissionResponseDto.builder()
                .id(permission.getId())
                .authority(permission.getAuthority())
                .description(permission.getDescription())
                .build();
    }

}
