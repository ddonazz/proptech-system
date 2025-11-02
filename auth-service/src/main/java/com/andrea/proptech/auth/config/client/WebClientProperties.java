package com.andrea.proptech.auth.config.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "proptech.webclient")
public class WebClientProperties {

    private Map<String, ClientConfig> clients;

    @Data
    public static class ClientConfig {
        private String baseUrl;
        private Duration connectTimeout = Duration.ofSeconds(5);
        private Duration readTimeout = Duration.ofSeconds(5);
    }

}
