package com.andrea.proptech.user.role.web;

import com.andrea.proptech.user.role.service.RoleService;
import com.andrea.proptech.user.role.web.dto.RoleDto;
import com.andrea.proptech.user.validation.OnCreate;
import com.andrea.proptech.user.validation.OnUpdate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable String id) {
        RoleDto roleDto = roleService.getRole(id);
        return ResponseEntity.ok(roleDto);
    }

    @GetMapping()
    public ResponseEntity<Page<RoleDto>> getRoles(Pageable pageable) {
        Page<RoleDto> roleDtos = roleService.getRoles(pageable);
        return ResponseEntity.ok(roleDtos);
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Validated({OnCreate.class}) @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRole.name())
                .toUri();

        return ResponseEntity.created(location).body(createdRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable String id, @Validated({OnUpdate.class}) @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.updateRole(id, roleDto);

        return ResponseEntity.ok(createdRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

}
