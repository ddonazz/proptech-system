package com.andrea.proptech.auth.web.client;

import com.andrea.proptech.core.security.web.dto.LoginRequest;
import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/internal/v1/users/validate")
    UserDetailsResponse validateCredentials(@RequestBody LoginRequest loginRequest);
    
}
