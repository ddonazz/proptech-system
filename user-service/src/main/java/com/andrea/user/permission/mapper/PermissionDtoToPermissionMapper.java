package com.andrea.user.permission.mapper;

import com.andrea.user.permission.PermissionName;
import com.andrea.user.permission.data.Permission;
import com.andrea.user.permission.web.dto.PermissionDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PermissionDtoToPermissionMapper implements Function<PermissionDto, Permission> {

    @Override
    public Permission apply(@NonNull PermissionDto permissionDto) {
        return new Permission(PermissionName.valueOf(permissionDto.name()));
    }

}
