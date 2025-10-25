package com.andrea.proptech.user.role.web;

import com.andrea.proptech.user.role.service.RoleService;
import com.andrea.proptech.user.role.web.dto.request.RoleCreateDto;
import com.andrea.proptech.user.role.web.dto.request.RoleUpdateDto;
import com.andrea.proptech.user.role.web.dto.response.RoleResponseDto;
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
    public ResponseEntity<RoleResponseDto> getRole(@PathVariable Long id) {
        RoleResponseDto response = roleService.getRole(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get roles")
    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_role:read')")
    public ResponseEntity<Page<RoleResponseDto>> getRoles(Pageable pageable) {
        Page<RoleResponseDto> response = roleService.getRoles(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a role")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_role:create')")
    public ResponseEntity<RoleResponseDto> createRole(@Validated @RequestBody RoleCreateDto request) {
        RoleResponseDto response = roleService.createRole(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.name())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Update a role")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_role:update')")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Long id, @Validated @RequestBody RoleUpdateDto roleUpdateDto) {
        RoleResponseDto updateRole = roleService.updateRole(id, roleUpdateDto);

        return ResponseEntity.ok(updateRole);
    }

    @Operation(summary = "Delete a role")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_role:delete')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

}
