package com.uce.cartitem.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private IdDto id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private String token;
}
