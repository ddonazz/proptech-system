package com.andrea.proptech.user.role.service;

import com.andrea.proptech.core.exception.ResourceInUseException;
import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.user.exception.UserErrorCodes;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.data.RoleRepository;
import com.andrea.proptech.user.role.mapper.RoleCreateDtoToRoleMapper;
import com.andrea.proptech.user.role.mapper.RoleToRoleResponseDtoMapper;
import com.andrea.proptech.user.role.web.dto.request.RoleCreateDto;
import com.andrea.proptech.user.role.web.dto.request.RoleUpdateDto;
import com.andrea.proptech.user.role.web.dto.response.RoleResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final RoleToRoleResponseDtoMapper roleToRoleResponseDtoMapper;
    private final RoleCreateDtoToRoleMapper roleCreateDtoToRoleMapper;

    @Transactional(readOnly = true)
    public RoleResponseDto getRole(Long id) {
        Role role = retrieveRole(id);
        return roleToRoleResponseDtoMapper.apply(role);
    }

    @Transactional(readOnly = true)
    public Page<RoleResponseDto> getRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(roleToRoleResponseDtoMapper);
    }

    @Transactional
    public RoleResponseDto createRole(RoleCreateDto request) {
        Set<Permission> foundPermissions = computePermissions(request.permissions());

        Role role = roleCreateDtoToRoleMapper.apply(request);
        role.setPermissions(foundPermissions);

        Role savedRole = roleRepository.save(role);
        return roleToRoleResponseDtoMapper.apply(savedRole);
    }

    @Transactional
    public RoleResponseDto updateRole(Long id, RoleUpdateDto roleUpdateDto) {
        Role role = retrieveRole(id);

        Set<Permission> foundPermissions = computePermissions(roleUpdateDto.permissions());
        role.setPermissions(foundPermissions);

        Role savedRole = roleRepository.save(role);
        return roleToRoleResponseDtoMapper.apply(savedRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        Role role = retrieveRole(id);
        if (!role.getUsers().isEmpty()) {
            throw new ResourceInUseException(UserErrorCodes.ROLE_IN_USE, id);
        }

        roleRepository.deleteById(id);
    }

    private Role retrieveRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UserErrorCodes.ROLE_NOT_FOUND, id));
    }

    private Set<Permission> computePermissions(Collection<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }

        return permissionRepository.findAllByIdIn(permissionIds);
    }
}
