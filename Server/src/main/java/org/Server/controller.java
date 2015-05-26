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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ashraf on 5/26/15.
 */
@RestController
public class controller {
    @Autowired
    queueService queueService;

    @RequestMapping(value = "/")
    public User index() {
        User revo = new User("revo");
        User user = (User) queueService.sendToMq(revo);
        System.out.println("i send " + revo.getName() + " and recive " + user.getName());
        return user;
    }
}
