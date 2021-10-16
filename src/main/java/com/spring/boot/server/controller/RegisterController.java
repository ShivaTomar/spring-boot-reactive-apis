package com.spring.boot.server.controller;

import com.spring.boot.server.model.Student;
import com.spring.boot.server.security.annotations.SecuredRoute;
import com.spring.boot.server.security.rules.SecurityRule;
import com.spring.boot.server.service.StudentService;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@Slf4j
@CrossOrigin
@AllArgsConstructor
@Validated
public class RegisterController {
    private final StudentService studentService;

    @SecuredRoute(SecurityRule.IS_ANONYMOUS)
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    Single<String> register(@Valid @RequestBody Student student) {
        log.info("Register student request with email: {}", student.getEmail());

        return studentService
                .addStudent(student)
                .map(successMessage -> {
                    log.info(successMessage);
                    return successMessage;
                })
                .onErrorResumeNext(err -> {
                    log.info("Registration failed for student: {}", student.getEmail());

                    return Single.error(
                            new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST, "Student with this email already exists."
                            )
                    );
                });
    }

}
