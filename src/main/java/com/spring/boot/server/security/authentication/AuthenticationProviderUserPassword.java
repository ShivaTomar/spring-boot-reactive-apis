package com.spring.boot.server.security.authentication;

import com.spring.boot.server.model.Student;
import com.spring.boot.server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String identity = authentication.getName();
        String secret = authentication.getCredentials().toString();

        Optional<Student> student = studentService.getStudentByEmail(identity);

        if (student.isPresent()) {
            if (passwordEncoder.matches(secret, student.get().getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        identity,
                        "",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + student.get().getRole()))
                );
            }

            throw new BadCredentialsException("Incorrect Password");
        }

        throw new BadCredentialsException(String.format("No User found with this email: %s.", identity));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

}
