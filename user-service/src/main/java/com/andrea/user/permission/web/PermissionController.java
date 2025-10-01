package com.andrea.user.permission.web;

import com.andrea.user.permission.service.PermissionService;
import com.andrea.user.permission.web.dto.PermissionDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/permissions")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<PermissionDto>> getAllPermissions(Pageable pageable) {
        Page<PermissionDto> permissions = permissionService.findAll(pageable);
        return ResponseEntity.ok(permissions);
    }
}
