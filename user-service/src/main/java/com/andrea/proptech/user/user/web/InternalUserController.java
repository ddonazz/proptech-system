package com.andrea.proptech.user.user.web;

import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import com.andrea.proptech.user.user.service.InternalUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final InternalUserService internalUserService;

    @Operation(summary = "Get a user by their ID")
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserDetailsResponse> byUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(internalUserService.getUserByUsername(username));
    }

}
