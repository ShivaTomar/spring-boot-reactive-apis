package com.spring.boot.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class EmailPasswordCredentials {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
