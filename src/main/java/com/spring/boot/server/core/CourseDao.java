package com.spring.boot.server.core;

import com.spring.boot.server.db.Storage;
import com.spring.boot.server.model.Course;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CourseDao implements Dao<Course> {
    private final Storage storage;

    @Override
    public Maybe<Course> get(Integer id) {
        return Single.just(Optional.ofNullable(storage.getCourses().get(id)))
                .filter(course -> course.isPresent())
                .map(course -> course.get());
    }

    @Override
    public Flowable<Course> getAll() {
        return Flowable.fromIterable(storage.getCourses().values());
    }

    @Override
    public Single<Boolean> save(Course course) {
        return Single.just(Optional.ofNullable(storage.getCourses().put(course.getId(), course)))
                .map(success -> success.isPresent());
    }

    @Override
    public Single<Boolean> update(Course course) {
        return Single.just(Optional.ofNullable(storage.getCourses().put(course.getId(), course)))
                .map(success -> success.isPresent());
    }

    @Override
    public Single<Boolean> delete(Course course) {
        return Single.just(Optional.of(storage.getCourses().remove(course.getId(), course)))
                .map(success -> success.isPresent());
    }

    public Integer getDataSize() {
        return storage.getCourses().size();
    }

}
