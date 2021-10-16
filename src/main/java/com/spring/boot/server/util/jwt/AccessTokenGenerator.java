package com.spring.boot.server.util.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccessTokenGenerator {
    private final JwtUtil jwtUtil;

    public AccessToken generate(Authentication authentication) {
        return new AccessToken(jwtUtil.generateToken(authentication));
    }

}
