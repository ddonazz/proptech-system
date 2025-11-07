package com.andrea.proptech.user.permission.web;

import com.andrea.proptech.core.dto.PageResponse;
import com.andrea.proptech.user.permission.service.PermissionService;
import com.andrea.proptech.user.permission.web.dto.response.PermissionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Permission Management", description = "APIs for managing permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "Retrieve available permissions")
    @GetMapping
    public ResponseEntity<PageResponse<PermissionResponseDto>> getAllPermissions(Pageable pageable) {
        Page<PermissionResponseDto> permissionDtosPage = permissionService.findAll(pageable);
        PageResponse<PermissionResponseDto> response = PageResponse.fromPage(permissionDtosPage);
        return ResponseEntity.ok(response);
    }
}
