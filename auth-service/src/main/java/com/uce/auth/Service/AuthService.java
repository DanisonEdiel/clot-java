package com.uce.auth.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uce.auth.Dto.UserMessage;

@Service
public class AuthService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Map<String, CountDownLatch> responseLatches = new ConcurrentHashMap<>();
    private final Map<String, UserMessage> pendingResponses = new ConcurrentHashMap<>();

    public ResponseEntity<?> authenticateUser(String email, String password) {
        UserMessage userMessage = new UserMessage();
        userMessage.setEmail(email);
        userMessage.setPassword(password);

        String correlationId = UUID.randomUUID().toString();
        userMessage.setId(correlationId);

        CountDownLatch latch = new CountDownLatch(1);
        responseLatches.put(correlationId, latch);

        rabbitTemplate.convertAndSend("auth.verification.queue", userMessage);

        UserMessage responseMessage = awaitResponse(correlationId, latch);

        if (responseMessage != null && responseMessage.isEnabled()) {
            boolean passwordMatch = passwordEncoder.matches(password, responseMessage.getPassword());
            if (passwordMatch) {
                return ResponseEntity.ok("Welcome!");
            } else {
                return ResponseEntity.badRequest().body("Invalid credentials.");
            }
        } else {
            return ResponseEntity.badRequest()
                    .body("Please confirm your account via the email sent during registration.");
        }
    }

    private UserMessage awaitResponse(String correlationId, CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return pendingResponses.remove(correlationId);
    }

    @RabbitListener(queues = "auth.response.queue")
    public void receiveAuthResponse(UserMessage userMessage) {
        CountDownLatch latch = responseLatches.remove(userMessage.getId());
        if (latch != null) {
            pendingResponses.put(userMessage.getId(), userMessage);
            latch.countDown();
        }
    }
}
