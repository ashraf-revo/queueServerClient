/*
 * Copyright (c) 2015. revo
 */

package Client;


import org.revo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ashraf on 5/26/15.
 */
@Service
public class userService {
    @Autowired
    ApplicationContext ctx;

    @Autowired
    RabbitTemplate mq;


    public User addUser(User user) {
        String name = "ashraf";
        System.out.println("i recive " + user.getName() + " and send to " + name);
        user.setName(name);
        return user;
    }


    @RabbitListener(queues = "rib.created.queue")
    public void queueListener(User user, Message message) {
        Object service = ctx.getBean(message.getMessageProperties().getHeaders().get("ServiceName").toString());
        Method method = null;
        try {
            method = service.getClass().getMethod(
                    message.getMessageProperties().getHeaders().get("ServiceMethodName").toString(), user.getClass());
        } catch (NoSuchMethodException e) {
        }
        try {
            mq.convertAndSend(message.getMessageProperties().getReplyTo(), method.invoke(service, user));
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
