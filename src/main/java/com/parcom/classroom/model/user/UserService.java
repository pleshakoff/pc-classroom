package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.student.Student;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {
    User create(String email);

    abstract User current();

    void addUserToGroup(@NotNull Group group, @NotNull User user);

    void addUserToStudent(@NotNull Student student, @NotNull User user);

    User getById(@NotNull Long id);

    List<User> allInGroup();

    @Transactional
    User update(Long id, UserUpdateDto userUpdateDto);

    @Transactional
    void delete(Long id);

    void registerInSecurity(UserCreateDto userCreateDto);
}
