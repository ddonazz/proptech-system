package com.andrea.proptech.user.role.web;

import com.andrea.proptech.user.role.service.RoleService;
import com.andrea.proptech.user.role.web.dto.RoleDto;
import com.andrea.proptech.user.validation.OnCreate;
import com.andrea.proptech.user.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
@Tag(name = "Role Management", description = "APIs for managing roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Get a role")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_role:read')")
    public ResponseEntity<RoleDto> getRole(@PathVariable Long id) {
        RoleDto roleDto = roleService.getRole(id);
        return ResponseEntity.ok(roleDto);
    }

    @Operation(summary = "Get roles")
    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_role:read')")
    public ResponseEntity<Page<RoleDto>> getRoles(Pageable pageable) {
        Page<RoleDto> roleDtos = roleService.getRoles(pageable);
        return ResponseEntity.ok(roleDtos);
    }

    @Operation(summary = "Create a role")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_role:create')")
    public ResponseEntity<RoleDto> createRole(@Validated({OnCreate.class}) @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRole.name())
                .toUri();

        return ResponseEntity.created(location).body(createdRole);
    }

    @Operation(summary = "Update a role")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_role:update')")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @Validated({OnUpdate.class}) @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.updateRole(id, roleDto);

        return ResponseEntity.ok(createdRole);
    }

    @Operation(summary = "Delete a role")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_role:delete')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

}
