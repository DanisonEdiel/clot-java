package com.uce.email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.uce.email.Dto.UserMessage;
import com.uce.email.Service.ServiceImp.EmailDTOSeriviceImp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailDTOService implements EmailDTOSeriviceImp {
    @Value("${email.sender}")
    private String emailUser;
    @Value("${token.service.url}")
    private String tokenUrl;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String[] toUser, String subject, String message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            messageHelper.setFrom(emailUser);
            messageHelper.setTo(toUser);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true); // 'true' indicates HTML content
        } catch (MessagingException e) {
            e.printStackTrace();
            // Manejo de excepciones adecuado
        }

        mailSender.send(mimeMessage);
    }

    @Override
    public void reciveMail(UserMessage userMessage) {
        String[] toUser = { userMessage.getEmail() };
        String subject = "Confirm your account";
        String message = "<p>Please confirm your account by clicking the link below:</p>" +
                "<a href=\"" + tokenUrl + "?token=" + userMessage.getToken() + "\">Confirm your account</a>";

        sendEmail(toUser, subject, message);
    }
}
