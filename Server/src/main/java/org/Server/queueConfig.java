/*
 * Copyright (c) 2015. revo
 */

package org.Server;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ashraf on 5/26/15.
 */
@Configuration
@EnableAutoConfiguration
public class queueConfig {
    @Autowired
    AmqpAdmin rabbitAdmin;
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        final Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(jsonConverter);
        Queue ribCreatedQueue = new Queue("rib.created.queue");
        rabbitAdmin.declareQueue(ribCreatedQueue);
        return rabbitTemplate;
    }
}
