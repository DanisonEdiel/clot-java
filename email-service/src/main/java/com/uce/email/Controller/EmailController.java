package com.uce.email.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.email.Dto.EmailDTO;
import com.uce.email.Service.EmailDTOService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/email")
@Tag(name = "Api Rest for brands use Swagger 3 - Email")
public class EmailController {
    @Autowired
    private EmailDTOService dtoService;;

    @PostMapping("/sendMessage")
    @Operation(summary = "This endpoint is used to send message for user")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO) {
        System.out.println("Message received " + emailDTO);

        dtoService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("status", "Send");
        return ResponseEntity.ok(response);
    }
}
