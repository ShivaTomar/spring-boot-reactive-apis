package com.spring.boot.server.model;

import com.spring.boot.server.util.validators.Age;
import com.spring.boot.server.util.validators.Email;
import com.spring.boot.server.util.validators.Password;
import com.spring.boot.server.util.validators.groups.First;
import io.reactivex.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Password(groups = First.class)
@AllArgsConstructor
@GroupSequence({Student.class, First.class})
public class Student {
    @Nullable
    private Integer id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email(groups = First.class)
    @NotBlank
    private String email;

    @NotBlank
    @ToString.Exclude
    @Size(min = 6, max = 32, message = "Password cannot be less than 6 and greater than 32")
    private String password;

    @Age(groups = First.class)
    @NotNull
    private Integer age;

    @NotBlank
    private String education;

    @Nullable
    private String role;

    @Nullable
    private List<Course> courses;
}
