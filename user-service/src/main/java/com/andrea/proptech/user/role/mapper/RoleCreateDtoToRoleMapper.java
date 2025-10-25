package com.andrea.proptech.user.role.mapper;

import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.web.dto.request.RoleCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RoleCreateDtoToRoleMapper implements Function<RoleCreateDto, Role> {

    @Override
    public Role apply(@NonNull RoleCreateDto dto) {
        Role entity = new Role();
        entity.setName(dto.name());
        entity.setDescription(dto.description());

        return entity;
    }
}
