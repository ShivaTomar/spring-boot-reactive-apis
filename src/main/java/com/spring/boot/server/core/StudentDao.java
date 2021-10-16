package com.spring.boot.server.core;

import com.spring.boot.server.db.Storage;
import com.spring.boot.server.model.Student;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@AllArgsConstructor
public class StudentDao implements Dao<Student> {
    private final Storage storage;

    @Override
    public Maybe<Student> get(Integer id) {
        return Flowable.fromIterable(storage.getStudentsRecord())
                .filter(student -> student.getId().equals(id))
                .firstElement();
    }

    public Optional<Student> get(String email) {
        return storage.getStudentsRecord().stream().filter(student -> student.getEmail().equals(email)).findFirst();
    }

    @Override
    public Flowable<Student> getAll() {
        return Flowable.fromIterable(storage.getStudentsRecord());
    }

    @Override
    public Single<Boolean> save(Student student) {
        return Single.just(storage.getStudentsRecord().add(student));
    }

    @Override
    public Single<Boolean> update(Student student) {
        return Single.just(storage.getStudentsRecord())
                .map(students -> {
                    var counter = 0;
                    var updateSuccess = false;
                    for(Student std: students) {
                        if (std.getId().equals(student.getId())) {
                            storage.getStudentsRecord().set(counter, student);
                            updateSuccess = true;
                            break;
                        }
                        counter++;
                    }
                    return updateSuccess;
                });
    }

    @Override
    public Single<Boolean> delete(Student student) {
       return Flowable.fromIterable(storage.getStudentsRecord())
               .filter(std -> std.getId().equals(student.getId()))
               .firstElement()
               .map(target -> storage.getStudentsRecord().remove(target))
               .isEmpty();
    }

    public Integer getDataSize() {
        return storage.getStudentsRecord().size();
    }

}
