/*
 * Copyright (c) 2015. revo
 */

package org.Server;

import org.revo.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ashraf on 5/26/15.
 */
@Service
public class queueService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public Object sendToMq(User revo) {
        return rabbitTemplate.convertSendAndReceive("rib.created.queue", revo,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setHeader("ServiceName", "userService");
                        message.getMessageProperties().setHeader("ServiceMethodName", "addUser");
                        return message;
                    }
                });
    }
}
