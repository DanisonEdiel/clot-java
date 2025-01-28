package com.uce.token.Service.ServiceImp;

import com.uce.token.Model.Token;

public interface TokenServiceImp {
    Token createToken(String userId);

    boolean validateToken(String token);

    Token findTokenByToken(String token);

}
