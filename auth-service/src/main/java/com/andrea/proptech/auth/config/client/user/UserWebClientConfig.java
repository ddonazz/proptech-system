package com.andrea.proptech.auth.config.client.user;

import com.andrea.proptech.auth.config.client.WebClientProperties;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class UserWebClientConfig {

    private final WebClientProperties webClientProperties;

    private final static String USER_SERVICE = "user-service";

    @Bean("user-service-web-client")
    WebClient internalUserWebClient() {

        WebClientProperties.ClientConfig config = webClientProperties.getClients().get(USER_SERVICE);
        
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(config.getReadTimeout())
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(config.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(config.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(config.getBaseUrl()) // Ora è solo http://user-service:8080
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
