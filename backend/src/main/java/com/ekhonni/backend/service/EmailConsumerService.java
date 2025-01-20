package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailTaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author: Safayet Rafi
 * Date: 19/01/25
 */

@Service
@AllArgsConstructor
public class EmailConsumerService {


    private final EmailService emailService;

    @RabbitListener(queues = {"${rabbitmq.email-configuration.queue}"})
    public void listen(EmailTaskDTO emailTaskDTO) {
        emailService.send(emailTaskDTO);
    }
}
