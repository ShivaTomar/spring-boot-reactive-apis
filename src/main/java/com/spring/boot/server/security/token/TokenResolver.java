package com.spring.boot.server.security.token;

import com.spring.boot.server.util.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class TokenResolver {
    private final JwtUtil jwtUtil;

    public Optional<String> resolveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(JwtUtil.HEADER_STRING);
        return authorizationHeader == null ? Optional.empty() : extractTokenFromAuthorization(authorizationHeader);
    }

    public Optional<Authentication> validateToken(String token) {
        try {
            String subject = jwtUtil.getUsernameFromToken(token);
            return Optional.of(jwtUtil.getAuthenticationToken(token, subject));
        } catch (Exception exception) {
            log.info("Failed to parse JWT: {}", exception.getMessage());
        }

        return Optional.empty();
    }

    private Optional<String> extractTokenFromAuthorization(String authorization) {
        StringBuilder sb = new StringBuilder();
        String prefix = JwtUtil.PREFIX;

        sb.append(prefix);
        sb.append(" ");

        String str = sb.toString().toLowerCase(Locale.ROOT);
        if (authorization.toLowerCase(Locale.ROOT).startsWith(str)) {
            return Optional.of(authorization.substring(str.length()));
        } else {
            log.info("{} does not start with {}", authorization, str);
            return Optional.empty();
        }
    }

}
