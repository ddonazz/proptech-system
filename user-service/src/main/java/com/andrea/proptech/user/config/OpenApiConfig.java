package com.andrea.proptech.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "User Service API", version = "v1"),
        security = @SecurityRequirement(name = "oAuth2"),
        servers = @Server(description = "API Gateway URL", url = "http://localhost:8080")
)
@SecurityScheme(
        name = "oAuth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:9000/oauth2/authorize",
                        tokenUrl = "http://localhost:9000/oauth2/token",
                        scopes = {
                                @OAuthScope(name = "admin:access", description = "Scope for administrator access"),
                                @OAuthScope(name = "role:read", description = "Scope for reading roles"),
                                @OAuthScope(name = "role:write", description = "Scope for writing/modifying roles"),
                                @OAuthScope(name = "user:read", description = "Scope for reading users"),
                                @OAuthScope(name = "user:write", description = "Scope for writing/modifying users")
                        }
                )
        )
)
public class OpenApiConfig {
}