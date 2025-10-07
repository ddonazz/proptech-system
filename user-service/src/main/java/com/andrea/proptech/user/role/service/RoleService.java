package com.andrea.proptech.user.role.service;

import com.andrea.proptech.user.exception.ResourceInUseException;
import com.andrea.proptech.user.exception.ResourceNotFoundException;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.data.PermissionRepository;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.data.RoleRepository;
import com.andrea.proptech.user.role.mapper.RoleDtoToRoleMapper;
import com.andrea.proptech.user.role.mapper.RoleToRoleDtoMapper;
import com.andrea.proptech.user.role.web.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final RoleToRoleDtoMapper roleToRoleDtoMapper;
    private final RoleDtoToRoleMapper roleDtoToRoleMapper;

    @Transactional(readOnly = true)
    public RoleDto getRole(String id) {
        Role role = retrieveRole(id);
        return roleToRoleDtoMapper.apply(role);
    }

    @Transactional(readOnly = true)
    public Page<RoleDto> getRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(roleToRoleDtoMapper);
    }

    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        Set<Permission> foundPermissions = computePermissions(roleDto.permissions());

        Role role = roleDtoToRoleMapper.apply(roleDto);
        role.setPermissions(foundPermissions);

        Role savedRole = roleRepository.save(role);
        return roleToRoleDtoMapper.apply(savedRole);
    }

    @Transactional
    public RoleDto updateRole(String id, RoleDto roleDto) {
        Role role = retrieveRole(id);

        Set<Permission> foundPermissions = computePermissions(roleDto.permissions());
        role.setPermissions(foundPermissions);

        Role savedRole = roleRepository.save(role);
        return roleToRoleDtoMapper.apply(savedRole);
    }

    @Transactional
    public void deleteRole(String id) {
        Role role = retrieveRole(id);
        if (!role.getUsers().isEmpty()) {
            throw new ResourceInUseException(
                    "Unable to delete role with ID '" + id + "' as it is currently in use by one or more users.");
        }

        roleRepository.deleteById(id);
    }

    private Role retrieveRole(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: '" + id + "'."));
    }

    private Set<Permission> computePermissions(Collection<PermissionDto> permissionDtos) {
        if (permissionDtos == null || permissionDtos.isEmpty()) {
            return new HashSet<>();
        }

        return permissionRepository.findAllByNameIn(permissionDtos.stream()
                .map(permissionDto -> String.valueOf(permissionDto.name()))
                .collect(Collectors.toSet()));
    }
}
