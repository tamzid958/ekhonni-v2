package com.ekhonni.backend.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Author: Safayet Rafi
 * Date: 19/01/25
 */

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${rabbitmq-custom.email-configuration.queue}")
    private String emailQueue;

    @Value("${rabbitmq-custom.email-configuration.exchange}")
    private String emailExchange;

    @Value("${rabbitmq-custom.email-configuration.routing-key}")
    private String emailRoutingKey;

    @Value("${rabbitmq-custom.image-upload.exchange}")
    private String imageUploadExchange;

    @Value("${rabbitmq-custom.image-upload.product-routing-key}")
    private String productRoutingKey;

    @Value("${rabbitmq-custom.image-upload.category-routing-key}")
    private String categoryRoutingKey;

    @Value("${rabbitmq-custom.image-upload.product-queue}")
    private String productQueue;

    @Value("${rabbitmq-custom.image-upload.category-queue}")
    private String categoryQueue;





    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue, true);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(emailExchange);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.
                bind(emailQueue).
                to(emailExchange).
                with(emailRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue productQueue() {
        return new Queue(productQueue, true);
    }

    @Bean
    public Queue categoryQueue() {
        return new Queue(categoryQueue, true);
    }

    @Bean
    public TopicExchange imageUploadExchange() {
        return new TopicExchange("imageUploadExchange");
    }

    @Bean
    public Binding productBinding(Queue productQueue, TopicExchange imageUploadExchange) {
        return BindingBuilder.bind(productQueue).to(imageUploadExchange).with(productRoutingKey);
    }

    @Bean
    public Binding categoryBinding(Queue categoryQueue, TopicExchange imageUploadExchange) {
        return BindingBuilder.bind(categoryQueue).to(imageUploadExchange).with(categoryRoutingKey);
    }


}
