package com.andrea.proptech.core.messaging.user.rabbitmq;

import com.andrea.proptech.core.messaging.rabbitmq.RabbitMqContract;
import lombok.Getter;

@Getter
public enum UserQueueDefinition implements RabbitMqContract {

    USER_CREATED(UserQueueConstants.USER_CREATED_QUEUE_NAME, "user-service.user.created.dlq", "user.created");

    private final String queueName;
    private final String dlqName;
    private final String routingKey;

    UserQueueDefinition(String queueName, String dlqName, String routingKey) {
        this.queueName = queueName;
        this.dlqName = dlqName;
        this.routingKey = routingKey;
    }
}
