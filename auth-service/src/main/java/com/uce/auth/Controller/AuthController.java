package com.uce.auth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.auth.Dto.LoginRequest;
import com.uce.auth.Service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Api Rest for brands use Swagger 3 - Authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    @Operation(summary = "This endpoint is used to authentication user")
    public ResponseEntity<?> authentication(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
    }

}