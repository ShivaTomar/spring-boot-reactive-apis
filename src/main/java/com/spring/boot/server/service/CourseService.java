package com.spring.boot.server.service;

import com.spring.boot.server.core.CourseDao;
import com.spring.boot.server.model.Course;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseDao courseDao;

    public Maybe<Course> getCourse(Integer id) {
        return courseDao.get(id);
    }

    public Flowable<Course> getCourseList() {
        return courseDao.getAll();
    }

    public Single<String> addCourse(Course course) {
        course.setId(generateCourseId());
        courseDao.save(course);

        return Single.just(String.format("Course: %s added successfully.", course.getName()));
    }

    Integer generateCourseId() {
        return courseDao.getDataSize() + 1;
    }

}
