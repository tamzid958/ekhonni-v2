package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailTaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author: Safayet Rafi
 * Date: 19/01/25
 */

@Service
@AllArgsConstructor
public class EmailProducerService {

    @Value("${rabbitmq.email-configuration.exchange}")
    private String emailExchange;

    @Value("${rabbitmq.email-configuration.routing-key}")
    private String emailRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public void send(EmailTaskDTO emailTaskDTO) {
        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, emailTaskDTO);
    }
}
