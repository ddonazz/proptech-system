package com.andrea.user.permission.service;

import com.andrea.user.permission.data.PermissionRepository;
import com.andrea.user.permission.mapper.PermissionToPermissionDtoMapper;
import com.andrea.user.permission.web.dto.PermissionDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionToPermissionDtoMapper permissionToPermissionDtoMapper;

    @Transactional(readOnly = true)
    public Page<PermissionDto> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(permissionToPermissionDtoMapper);
    }

}
