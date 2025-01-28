package com.uce.email.Service.ServiceImp;

import com.uce.email.Dto.UserMessage;

public interface EmailDTOSeriviceImp {
    void sendEmail(String[] toUser, String subject, String message);
    void reciveMail (UserMessage userMessage);
}
