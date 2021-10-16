package com.spring.boot.server.controller;

import com.spring.boot.server.model.Course;
import com.spring.boot.server.service.CourseService;
import com.spring.boot.server.security.annotations.SecuredRoute;
import com.spring.boot.server.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @GetMapping("/{id}")
    Single<Course> getCourse(@NotNull @PathVariable Integer id) {
        log.info("Request to get course with Id: {}", id);

        return courseService.getCourse(id)
                .map(student -> {
                    log.info("Course: {} successfully fetched", student);
                    return student;
                }).switchIfEmpty(Single.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Course available with this id.")));
    }

    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @GetMapping
    public Flowable<Course> getCourseList() {
        log.info("Request to get course list");

        return courseService.getCourseList()
                .map(courseList -> {
                    log.info("Courses: {} fetched", courseList);
                    return courseList;
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecuredRoute(SecurityRule.IS_AUTHENTICATED)
    @PostMapping
    public Single<String> addCourse(@Valid @RequestBody Course course) {
        log.info("Request to add course: {}", course);

        return courseService.addCourse(course)
                .map(success -> {
                    log.info(success);
                    return success;
                });
    }

}
