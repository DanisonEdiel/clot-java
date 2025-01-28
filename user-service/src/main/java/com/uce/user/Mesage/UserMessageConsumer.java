package com.uce.user.Mesage;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uce.user.Model.User;
import com.uce.user.Model.Dto.UserMesagge;
import com.uce.user.Repository.UserRepository;

@Service
public class UserMessageConsumer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RabbitListener(queues = "user.update.queue")
    public void consumeMessage(UserMesagge userMessage) {
        try {
            // Verificar si el ID no es nulo o vacío
            if (userMessage.getId() == null || userMessage.getId().isEmpty()) {
                System.out.println("Error: User ID is null or empty");
                return;
            }

            System.out.println("Message received in user-service: " + userMessage);

            // Intentar convertir el ID a ObjectId
            ObjectId objectId;
            try {
                objectId = new ObjectId(userMessage.getId());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid ObjectId format for ID: " + userMessage.getId());
                return;
            }

            Optional<User> optionalUser = userRepository.findById(objectId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (userMessage.getToken() != null) {
                    user.setToken(userMessage.getToken());
                }
                if (userMessage.getUsername() != null) {
                    user.setUsername(userMessage.getUsername());
                }
                if (userMessage.getEmail() != null) {
                    user.setEmail(userMessage.getEmail());
                }

                if (userMessage.isEnabled()) {
                    user.setEnabled(true);
                }

                userRepository.save(user);

                userMessage.setUsername(user.getUsername());
                userMessage.setEmail(user.getEmail());
                userMessage.setPassword(user.getPassword());
                userMessage.setEnabled(user.isEnabled());

                // Enviar el mensaje completo al email-service y auth-service
                rabbitTemplate.convertAndSend("email.queue", userMessage);
                rabbitTemplate.convertAndSend("auth.queue", userMessage);
                rabbitTemplate.convertAndSend("auth.response.queue", userMessage);

            } else {
                System.out.println("User not found for ID: " + userMessage.getId());
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while processing the message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "auth.queue")
    public void consumeAuthMessage(UserMesagge userMessage) {
        System.out.println("Authentication request received: " + userMessage);

        Optional<User> optionalUser = userRepository.findByEmail(userMessage.getEmail());
        boolean isValidUser = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            isValidUser = passwordEncoder.matches(userMessage.getPassword(), user.getPassword());
        }

        userMessage.setEnabled(isValidUser);
        rabbitTemplate.convertAndSend("auth.response.queue", userMessage);
    }

    @RabbitListener(queues = "auth.verification.queue")
    public void consumeVerificationRequest(UserMesagge userMessage) {
        System.out.println("Authentication verification received: " + userMessage);
        Optional<User> optionalUser = userRepository.findByEmail(userMessage.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            //user.setEnabled(true);
            //userRepository.save(user);
            UserMesagge responseMessage = new UserMesagge();
            responseMessage.setId(userMessage.getId());
            responseMessage.setEmail(user.getEmail());
            responseMessage.setPassword(user.getPassword());
            responseMessage.setEnabled(user.isEnabled());

            rabbitTemplate.convertAndSend("auth.response.queue", responseMessage);
        }
    }

}
