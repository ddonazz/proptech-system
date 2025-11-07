package com.andrea.proptech.user.user.web;

import com.andrea.proptech.core.dto.PageResponse;
import com.andrea.proptech.user.user.service.UserService;
import com.andrea.proptech.user.user.web.dto.request.UserCreateDto;
import com.andrea.proptech.user.user.web.dto.request.UserUpdateDto;
import com.andrea.proptech.user.user.web.dto.response.UserResponseDto;
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
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get a user")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get users")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public ResponseEntity<PageResponse<UserResponseDto>> getUsers(Pageable pageable) {
        Page<UserResponseDto> userDtosPage = userService.getUsers(pageable);

        PageResponse<UserResponseDto> response = PageResponse.fromPage(userDtosPage);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a user")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_user:create')")
    public ResponseEntity<UserResponseDto> createUser(@Validated @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto createdUser = userService.createUser(userCreateDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_user:update')")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Validated @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_user:delete')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}