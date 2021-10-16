package com.spring.boot.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CourseRequest {
    @NotNull
    private Integer courseId;

    @NotNull
    private Integer studentId;
}
