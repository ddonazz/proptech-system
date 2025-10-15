package com.andrea.proptech.user.user.web;

import com.andrea.proptech.user.user.service.UserService;
import com.andrea.proptech.user.user.web.dto.UserDto;
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
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get a user")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Get users")
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        Page<UserDto> userDtos = userService.getUsers(pageable);
        return ResponseEntity.ok(userDtos);
    }

    @Operation(summary = "Create a user")
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_user:create')")
    public ResponseEntity<UserDto> createUser(@Validated({OnCreate.class}) @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_user:update')")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Validated({OnUpdate.class}) @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
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