package com.spring.boot.server.db;

import com.spring.boot.server.model.Course;
import com.spring.boot.server.model.Student;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class Storage {
    private final Map<Integer, Course> courses;
    private final List<Student> studentsRecord;

    Storage() {
        this.studentsRecord = new ArrayList<>();
        this.courses = new HashMap<>();

        this.courses.put(1, new Course(1, "Spring boot"));
        this.courses.put(2, new Course(2, "Micronaut"));
        this.courses.put(3, new Course(3, "NodeJs"));
        this.courses.put(4, new Course(4, "Boxing"));
        this.courses.put(5, new Course(5, "Redis"));
    }

}
