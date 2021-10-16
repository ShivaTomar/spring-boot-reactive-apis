package com.spring.boot.server.security.util;

import com.spring.boot.server.security.filters.SecurityFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Component
public class SecurityUtil {

    public boolean isRejectionFound() {
        var rejection = getAttribute(SecurityFilter.REJECTION);
        return rejection.isPresent();
    }

    public boolean isAuthenticated() {
        var authentication = getAttribute(SecurityFilter.AUTHENTICATION);
        return authentication.isPresent();
    }

    public Optional<Object> getToken() {
        var token = getAttribute(SecurityFilter.TOKEN);
        return Optional.ofNullable(token);
    }

    private Optional<Object> getAttribute(String name) {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes().getAttribute(name, 0));
    }

}
