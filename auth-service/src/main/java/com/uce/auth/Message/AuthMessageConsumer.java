package com.uce.auth.Message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.auth.Dto.UserMessage;
import com.uce.auth.Service.AuthService;

@Service
public class AuthMessageConsumer {
    @Autowired
    private AuthService authService;

    @RabbitListener(queues = "auth.response.queue")
    public void receiveAuthResponse(UserMessage userMessage) {
        authService.receiveAuthResponse(userMessage);
    }
}
