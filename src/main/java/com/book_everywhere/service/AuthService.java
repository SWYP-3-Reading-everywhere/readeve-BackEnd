package com.book_everywhere.service;

import com.book_everywhere.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtUtil jwtUtil;


    public String createToken(String providerId, String role) {
        return jwtUtil.createJwt(providerId, role, 3600000L); // 1시간 동안 유효
    }
}
