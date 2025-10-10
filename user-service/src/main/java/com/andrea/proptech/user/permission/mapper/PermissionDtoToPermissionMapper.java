package com.andrea.proptech.user.permission.mapper;

import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PermissionDtoToPermissionMapper implements Function<PermissionDto, Permission> {

    @Override
    public Permission apply(@NonNull PermissionDto permissionDto) {
        return Permission.builder()
                .authority(permissionDto.authority())
                .description(permissionDto.description())
                .build();
    }

}
