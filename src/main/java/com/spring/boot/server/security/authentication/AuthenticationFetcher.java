package com.spring.boot.server.security.authentication;

import com.spring.boot.server.security.filters.SecurityFilter;
import com.spring.boot.server.security.token.TokenResolver;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthenticationFetcher {
    private final TokenResolver tokenResolver;

    public Optional<Authentication> fetchAuthentication(HttpServletRequest request) {
        Optional<String> token = tokenResolver.resolveToken(request);

        if (token.isEmpty()) {
            return Optional.empty();
        } else {
            String tokenValue = token.get();
            return tokenResolver.validateToken(tokenValue)
                    .map(authentication -> {
                        request.setAttribute(SecurityFilter.TOKEN, tokenValue);
                        return authentication;
                    });
        }

    }

}
