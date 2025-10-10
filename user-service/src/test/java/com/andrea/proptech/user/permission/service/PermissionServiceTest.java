package com.andrea.proptech.user.permission.service;

import com.andrea.proptech.core.security.permission.PermissionAuthority;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import com.andrea.proptech.user.permission.mapper.PermissionToPermissionDtoMapper;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionToPermissionDtoMapper permissionToPermissionDtoMapper;

    @InjectMocks
    private PermissionService permissionService;

    @Test
    void findAll_whenPermissionsExist_shouldReturnPagedPermissions() {
        Pageable pageable = PageRequest.of(0, 10);
        Permission permission = new Permission(PermissionAuthority.ADMIN_ACCESS);
        Page<Permission> page = new PageImpl<>(List.of(permission));
        PermissionDto permissionDto = PermissionDto.builder().build();

        when(permissionRepository.findAll(pageable)).thenReturn(page);
        when(permissionToPermissionDtoMapper.apply(permission)).thenReturn(permissionDto);

        Page<PermissionDto> result = permissionService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(permissionDto, result.getContent().getFirst());
    }

}
