package com.uce.user.Mesage;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.uce.user.Model.User;
import com.uce.user.Model.Dto.UserMesagge;

@Service
public class UserMesageProducer {
    private final RabbitTemplate rabbitTemplate;

    public UserMesageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(User user) {
        UserMesagge userMesagge = new UserMesagge();
        userMesagge.setId(user.getId().toString());
        userMesagge.setUsername(user.getUsername());
        userMesagge.setEmail(user.getEmail());
        userMesagge.setPassword(user.getPassword());
        userMesagge.setEnabled(user.isEnabled());
        userMesagge.setToken(user.getToken());
        rabbitTemplate.convertAndSend("token.queue", userMesagge);
        // rabbitTemplate.convertAndSend("email.queue", userMesagge);

    }

    public void updateUserWithToken(UserMesagge userMessage) {
        rabbitTemplate.convertAndSend("user.update.queue", userMessage);
    }
}
