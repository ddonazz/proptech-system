package com.andrea.proptech.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "User Service API", version = "v1"),
        security = @SecurityRequirement(name = "oAuth2")
)
@SecurityScheme(
        name = "oAuth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:9000/oauth2/authorize",
                        tokenUrl = "http://localhost:9000/oauth2/token",
                        scopes = {
                                @OAuthScope(name = "openid", description = "OpenID Connect scope"),
                                @OAuthScope(name = "profile", description = "Scope for reading profile"),
                                @OAuthScope(name = "api.read", description = "Scope for reading data")
                        }
                )
        )
)
public class OpenApiConfig {
}