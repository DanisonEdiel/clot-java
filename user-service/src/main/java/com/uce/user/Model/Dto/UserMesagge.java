package com.uce.user.Model.Dto;

import lombok.Data;

@Data
public class UserMesagge {
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private String token;
}
