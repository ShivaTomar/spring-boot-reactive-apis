package com.spring.boot.server.service;

import com.spring.boot.server.core.CourseDao;
import com.spring.boot.server.core.StudentDao;
import com.spring.boot.server.db.Storage;
import com.spring.boot.server.model.Course;
import com.spring.boot.server.model.CourseRequest;
import com.spring.boot.server.model.Student;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spring.boot.server.util.ApplicationUserRole.ADMIN;
import static com.spring.boot.server.util.ApplicationUserRole.STUDENT;

@Service
@AllArgsConstructor
public class StudentService {
    private final Storage storage;
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final PasswordEncoder passwordEncoder;

    public Single<String> addStudent(Student newStudent) {
        return Flowable.just(studentDao.get(newStudent.getEmail()))
                .filter(Optional::isEmpty)
                .map(result -> {
                    newStudent.setId(generateStudentId());
                    newStudent.setCourses(new ArrayList<>());
                    newStudent.setPassword(passwordEncoder.encode(newStudent.getPassword()));
                    newStudent.setRole(createUserRole(newStudent.getEmail()));
                    studentDao.save(newStudent);

                    return String.format("Registration successful for student with email: %s", newStudent.getEmail());
                }).firstOrError();
    }

    public Flowable<Student> getStudentsList() {
        return studentDao.getAll();
    }

    public Optional<Student> getStudentByEmail(String email) {
        return storage.getStudentsRecord().stream().filter(student -> student.getEmail().equals(email)).findFirst();
    }

    public Maybe<Student> getStudentById(Integer id) {
        return studentDao.get(id);
    }

    public Single<List<Course>> getStudentCoursesList(Integer id) {
        return studentDao.get(id)
                .map(Student::getCourses)
                .switchIfEmpty(Single.error(new RuntimeException("No student with this id is available.")));
    }

    public Single<List<Student>> getAllStudentsFromClass(String education) {
        return studentDao.getAll()
                .filter(student -> student.getEducation().equals(education))
                .toList();
    }

    public Single<String> addCourse(CourseRequest request) {
        return courseDao.get(request.getCourseId()).flatMap(course1 -> {
            return studentDao.get(request.getStudentId()).map(std -> {
                std.getCourses().add(course1);
                return "Course added successfully.";
            }).switchIfEmpty(Single.error(new RuntimeException("No student with this id is available."))).toMaybe();
        }).switchIfEmpty(Single.error(new RuntimeException("This course is not available. Please select a valid course!")));
    }

    public Integer generateStudentId() {
        return studentDao.getDataSize() + 1;
    }

    public String createUserRole(String email) {
        String domain = email.split("@")[1].substring(0, 5);
        return domain.equals("vdoit") ? ADMIN.name() : STUDENT.name();
    }

}
