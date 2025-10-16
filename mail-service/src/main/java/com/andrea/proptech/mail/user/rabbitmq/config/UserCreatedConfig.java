package com.andrea.proptech.mail.user.rabbitmq.config;

import com.andrea.proptech.core.messaging.user.rabbitmq.UserQueueDefinition;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCreatedConfig {

    @Bean
    public Queue userCreateQueue() {
        return new Queue(UserQueueDefinition.USER_CREATED.getQueueName(), true);
    }

    @Bean
    public Binding userCreateBinding(TopicExchange proptechExchange, Queue userCreateQueue) {
        return BindingBuilder
                .bind(userCreateQueue)
                .to(proptechExchange)
                .with(UserQueueDefinition.USER_CREATED.getRoutingKey());
    }
}
