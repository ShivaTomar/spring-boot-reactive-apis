package com.spring.boot.server.controller;

import com.spring.boot.server.model.EmailPasswordCredentials;
import com.spring.boot.server.security.authentication.AuthenticationProviderUserPassword;
import com.spring.boot.server.util.exception.UnauthorizedException;
import com.spring.boot.server.util.jwt.AccessToken;
import com.spring.boot.server.util.jwt.AccessTokenGenerator;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class LoginController {
    private final AccessTokenGenerator accessTokenGenerator;
    private final AuthenticationProviderUserPassword authenticationProviderUserPassword;

    @PostMapping("/login")
    Single<AccessToken> login(@Valid @RequestBody EmailPasswordCredentials credentials) {
        log.info("login request with email: {}", credentials.getEmail());

        try {
            Authentication authentication = authenticationProviderUserPassword.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );

            AccessToken token = accessTokenGenerator.generate(authentication);
            return Single.just(token);
        } catch (AuthenticationException exception) {
            return Single.error(new UnauthorizedException(exception.getMessage()));
        }

    }

}
