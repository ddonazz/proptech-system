package com.andrea.proptech.core.messaging.rabbitmq;

public interface RabbitMqContract {

    String getQueueName();

    String getDlqName();

    String getRoutingKey();
}
