package com.andrea.proptech.email.user.rabbitmq.listener;

import com.andrea.proptech.core.messaging.user.dto.UserCreatedDto;
import com.andrea.proptech.core.messaging.user.rabbitmq.UserQueueConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserListener {

    @RabbitListener(queues = UserQueueConstants.USER_CREATED_QUEUE_NAME)
    public void handleEmailNotification(UserCreatedDto userCreatedDto) {
    }

}
