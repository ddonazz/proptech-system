package com.andrea.proptech.user.permission.service;

import com.andrea.proptech.user.permission.data.PermissionRepository;
import com.andrea.proptech.user.permission.mapper.PermissionToPermissionResponseDtoMapper;
import com.andrea.proptech.user.permission.web.dto.response.PermissionResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionToPermissionResponseDtoMapper permissionToPermissionResponseDtoMapper;

    @Transactional(readOnly = true)
    public Page<PermissionResponseDto> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(permissionToPermissionResponseDtoMapper);
    }

}
