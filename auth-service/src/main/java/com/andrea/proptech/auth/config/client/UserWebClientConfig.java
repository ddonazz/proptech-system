package com.andrea.proptech.auth.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UserWebClientConfig {

    @Value("${internal-user-web-client.base-url:http://user-service/internal/v1/users}")
    private String internalUserApiBaseUrl;

    @Bean("internal-user-web-client")
    WebClient internalUserWebClient() {
        return WebClient.builder()
                .baseUrl(internalUserApiBaseUrl)
                .build();
    }
}
