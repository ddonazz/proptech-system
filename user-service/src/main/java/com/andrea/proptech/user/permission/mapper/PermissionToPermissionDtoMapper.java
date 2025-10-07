package com.andrea.proptech.user.permission.mapper;

import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PermissionToPermissionDtoMapper implements Function<Permission, PermissionDto> {

    @Override
    public PermissionDto apply(@NonNull Permission permission) {
        return PermissionDto.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }

}
