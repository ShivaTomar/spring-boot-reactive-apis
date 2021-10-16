package com.spring.boot.server.security.filters;

import com.spring.boot.server.security.authentication.AuthenticationFetcher;
import com.spring.boot.server.security.rules.SecurityRule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    public static final String AUTHENTICATION = "spring.AUTHENTICATION";
    public static final String REJECTION = "spring.security.REJECTION";
    public static final String TOKEN = "spring.token";
    private static final RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;

    private final Collection<SecurityRule> securityRules;
    private final AuthenticationFetcher authenticationFetcher;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (requestMatcher.matches(request)) {
            Authentication authentication = authenticationFetcher.fetchAuthentication(request).orElse(null);
            if (authentication == null) {
                request.setAttribute(REJECTION, HttpStatus.UNAUTHORIZED);
            } else {
                request.setAttribute(AUTHENTICATION, authentication);
                var authenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "", authentication.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
