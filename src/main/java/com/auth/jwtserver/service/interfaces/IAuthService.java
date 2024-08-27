package com.auth.jwtserver.service.interfaces;

import com.auth.jwtserver.dto.LoginDto;
import com.auth.jwtserver.dto.SignupDto;
import com.auth.jwtserver.dto.TokenDto;

public interface IAuthService {
    
    TokenDto signup(SignupDto dto);

    TokenDto login(LoginDto dto);

    void logout(TokenDto dto);

    void logoutAll(TokenDto dto);

    TokenDto accessToken(TokenDto dto);

    TokenDto refreshToken(TokenDto dto);
}