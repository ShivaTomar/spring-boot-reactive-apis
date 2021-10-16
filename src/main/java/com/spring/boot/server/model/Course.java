package com.spring.boot.server.model;

import io.reactivex.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Course {
    @Nullable
    private Integer id;

    @NotBlank
    private String name;
}
