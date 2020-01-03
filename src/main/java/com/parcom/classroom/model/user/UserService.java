package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.student.Student;
import org.springframework.security.access.annotation.Secured;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {
    User current();

    void addUserToGroup(@NotNull Group group, @NotNull User user);

    void addUserToStudent(@NotNull Student student, @NotNull User user);

    User getById(@NotNull Long id);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    List<User> allInGroup();

    @Transactional
    User create(String email);

    @Transactional
    User update(Long id, UserUpdateDto userUpdateDto);

    @Transactional
    void delete(Long id);

    void registerInSecurity(UserCreateDto userCreateDto);
}
