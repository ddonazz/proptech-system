package com.andrea.auth.web.client;

import com.andrea.core.web.dto.LoginRequest;
import com.andrea.core.web.dto.Token;
import com.andrea.core.web.dto.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceClient userServiceClient;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequest loginRequest) {
        UserDetailsResponse userDetailsResponseResponse = userServiceClient.validateCredentials(loginRequest);
        return ResponseEntity.ok(new Token(""));
    }
}
