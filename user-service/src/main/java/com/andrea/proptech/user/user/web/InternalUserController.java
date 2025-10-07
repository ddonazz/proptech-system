package com.andrea.proptech.user.user.web;

import com.andrea.proptech.core.security.web.dto.LoginRequest;
import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import com.andrea.proptech.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @PostMapping("/validate")
    public ResponseEntity<UserDetailsResponse> validateCredentials(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.validateCredentials(loginRequest.username(), loginRequest.password()));
    }
    
}
