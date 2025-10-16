package com.andrea.proptech.core.messaging.rabbitmq;

import lombok.Getter;

@Getter
public enum ExchangeName {

    PROPTECH_EXCHANGE("proptech-exchange");

    private final String name;

    ExchangeName(String name) {
        this.name = name;
    }

}
