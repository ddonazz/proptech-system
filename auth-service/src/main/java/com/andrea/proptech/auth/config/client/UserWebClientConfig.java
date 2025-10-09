package com.andrea.proptech.auth.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UserWebClientConfig {

    @Bean("internal-user-web-client")
    WebClient internalUserWebClient() {
        return WebClient.builder()
                // servizio utente interno
                .baseUrl("http://user-service:8080/internal/v1/users")
                .build();
    }
}
