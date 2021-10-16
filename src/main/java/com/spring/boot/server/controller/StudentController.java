package com.spring.boot.server.controller;

import com.spring.boot.server.model.CourseRequest;
import com.spring.boot.server.model.Student;
import com.spring.boot.server.model.Course;
import com.spring.boot.server.security.annotations.SecuredRoute;
import com.spring.boot.server.security.rules.SecurityRule;
import com.spring.boot.server.service.StudentService;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/students")
@Slf4j
@CrossOrigin
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @GetMapping("/{id}")
    Single<Student> getStudent(@NotBlank @PathVariable Integer id) {
        log.info("Request to get a student with id: {}", id);

        return studentService.getStudentById(id)
                .map(student -> {
                    log.info("Student: {} successfully fetched", student);
                    return student;
                }).switchIfEmpty(Single.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student with this Id not present.")));

    }

    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @GetMapping("/{id}/courses")
    Single<List<Course>> getStudentCourse(@NotBlank @PathVariable Integer id) {
        log.info("Request to get courses of student with Id: {}", id);

        return studentService.getStudentCoursesList(id)
                .map(courses -> {
                    log.info("Courses: {} fetched", courses);
                    return courses;
                })
                .onErrorResumeNext(err -> {
                    log.error(err.getMessage());
                    return Single.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage()));
                });
    }

    @SecuredRoute(SecurityRule.IS_ANONYMOUS)
    @PostMapping("/course")
    public Single<String> addCourse(@Valid @RequestBody CourseRequest request) {
        return studentService
                .addCourse(request)
                .onErrorResumeNext(err -> {
                    return Single.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage()));
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @GetMapping("/education/{edu}")
    Single<List<Student>> getAllStudentsFromClass(@NotBlank @PathVariable("edu") String education) {
        log.info("Request to get courses of student with class: {}", education);

        return studentService.getAllStudentsFromClass(education)
                .map(students -> {
                    log.info("Students: {} fetched", students);
                    return students;
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @GetMapping
    Flowable<Student> getStudentsList() {
        log.info("Request to get all the students.");

        return studentService.getStudentsList()
                .map(student -> {
                    log.info("Student: {} fetched", student);
                    return student;
                });
    }

}
