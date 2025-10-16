package com.andrea.proptech.mail.config;

import com.andrea.proptech.core.messaging.rabbitmq.ExchangeName;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public TopicExchange proptechExchange() {
        return new TopicExchange(ExchangeName.PROPTECH_EXCHANGE.getName());
    }

}
