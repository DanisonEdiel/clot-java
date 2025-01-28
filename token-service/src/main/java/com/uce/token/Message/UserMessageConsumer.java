package com.uce.token.Message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.token.Model.Token;
import com.uce.token.Model.UserMessage;
import com.uce.token.Service.TokenService;

@Service
public class UserMessageConsumer {
    @Autowired
    private TokenService tokenService;

    @RabbitListener(queues = "token.queue")
    public void consumeMessage(UserMessage userMessage) {
        Token newtoken = tokenService.createToken(userMessage.getId());
        System.out.println("Token create: " + newtoken.getToken());

    }
}
