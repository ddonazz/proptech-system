package com.andrea.proptech.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private final static String SECURITY_SCHEMA_NAME = "oAuth2";

    @Value("${openapi.server.url}")
    private String serverUrl;

    @Value("${openapi.oauth2.authorization-url}")
    private String authorizationUrl;

    @Value("${openapi.oauth2.token-url}")
    private String tokenUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .version("v1")
                )
                .servers(List.of(new Server().url(serverUrl).description("API Gateway URL")))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEMA_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEMA_NAME)
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(authorizationUrl)
                                                .tokenUrl(tokenUrl)
                                                .scopes(new Scopes()
                                                        .addString("admin:access", "Scope for administrator access")
                                                        .addString("role:read", "Scope for reading roles")
                                                        .addString("role:write", "Scope for writing/modifying roles")
                                                        .addString("user:read", "Scope for reading users")
                                                        .addString("user:write", "Scope for writing/modifying users")
                                                )
                                        )
                                )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA_NAME));
    }


}