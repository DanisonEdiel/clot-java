package com.uce.email.Message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.email.Dto.UserMessage;
import com.uce.email.Service.EmailDTOService;

@Service
public class UserMessageConsumer {
    @Autowired
    private EmailDTOService emailDTOService;

    @RabbitListener(queues = "email.queue")
    public void consumeMessage(UserMessage userMessage) {
        System.out.println("Message receive : " + userMessage);

        if (userMessage.getToken() == null) {
            System.out.println("Token is null for user: " + userMessage.getId());
        } else {
            System.out.println("Token receive: " + userMessage.getToken());
        }
        emailDTOService.reciveMail(userMessage);
    }
}
