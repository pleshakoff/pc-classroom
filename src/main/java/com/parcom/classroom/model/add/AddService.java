package com.parcom.classroom.model.add;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.classroom.model.user.*;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AddService {

    private final GroupService groupService;
    private final StudentService studentService;
    private final SchoolService schoolService;
    private final UserService userService;


    @Transactional
    public userSecurityDto registerByGroup(AddMemberDto addDto) {

        if (addDto.getIdGroup() == null) {
            throw new RuntimeException("Empty group");
        }

        Group group = groupService.getById(addDto.getIdGroup());
        User user = userService.create(addDto.email);
        userService.addUserToGroup(group,user);

        if (addDto.getIdStudent() != null) {
            Student student = studentService.getByOrNull(addDto.getIdStudent());
            userService.addUserToStudent(student, user);
        }

        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(user.getId()).
                email(addDto.getEmail()).
                password(addDto.getPassword()).
                passwordConfirm(addDto.passwordConfirm).
                role(UserUtils.ROLE_MEMBER).
                idGroup(addDto.getIdGroup()).build();

        return userService.registerInSecurity(userCreateDto);

    }


    @Transactional
    userSecurityDto registerByStudent(AddParentDto addDto) {

        if (addDto.getIdStudent() == null) {
            throw new RuntimeException("Empty student");
        }
        Student student = studentService.getById(addDto.getIdStudent());
        User user = userService.create(addDto.email);
        userService.addUserToStudent(student,user);
        userService.addUserToGroup(student.getGroup(),user);

        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(user.getId()).
                email(addDto.getEmail()).
                password(addDto.getPassword()).
                passwordConfirm(addDto.passwordConfirm).
                role(UserUtils.ROLE_PARENT).
                idGroup(student.getGroup().getId()).build();

        return userService.registerInSecurity(userCreateDto);
    }

    @Transactional
    userSecurityDto registerNewGroup(AddGroupDto addDto) {

          if (addDto.getIdSchool() == null && addDto.getNameSchool() == null)
            throw new  RuntimeException("Необходимо выбрать школу или ввести наименование");

        School school = schoolService.getOrCreate(addDto.getIdSchool(),addDto.getNameSchool());
        Group group = groupService.create(addDto.getNameGroup(),school);
        User user = userService.create(addDto.email);
        userService.addUserToGroup(group,user);

        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(user.getId()).
                email(addDto.getEmail()).
                password(addDto.getPassword()).
                passwordConfirm(addDto.passwordConfirm).
                role(UserUtils.ROLE_ADMIN).
                idGroup(group.getId()).build();

        return userService.registerInSecurity(userCreateDto);
    }



}
