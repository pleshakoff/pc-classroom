package com.parcom.classroom.services.add;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.classroom.model.user.User;
import com.parcom.classroom.model.user.UserCreateDto;
import com.parcom.classroom.model.user.UserService;
import com.parcom.exceptions.ParcomException;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class AddServiceImpl implements AddService {

    private final GroupService groupService;
    private final StudentService studentService;
    private final SchoolService schoolService;
    private final UserService userService;


    @Override
    @Transactional
    public User registerByGroup(AddMemberDto addDto) {

        if (addDto.getIdGroup() == null) {
            throw new ParcomException("group.can_not_be_null");
        }

        Group group = groupService.getById(addDto.getIdGroup());
        User user = userService.create(addDto.email);
        userService.addUserToGroup(group,user);

        if (addDto.getIdStudent() != null) {
            Student student = studentService.getById(addDto.getIdStudent());
            if (student != null) {
                userService.addUserToStudent(student, user);
            }
        }

        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(user.getId()).
                email(addDto.getEmail()).
                password(addDto.getPassword()).
                passwordConfirm(addDto.passwordConfirm).
                role(UserUtils.ROLE_MEMBER).
                idGroup(addDto.getIdGroup()).
                idStudent(addDto.getIdStudent()).build();

        userService.registerInSecurity(userCreateDto);
        return user;

    }


    @Override
    @Transactional
    public User registerByStudent(AddParentDto addDto) {

        if (addDto.getIdStudent() == null) {
            throw new ParcomException("student.can_not_be_null");
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
                idGroup(student.getGroup().getId()).
                idStudent(addDto.getIdStudent()).build();

                userService.registerInSecurity(userCreateDto);
                return user;
    }

    @Override
    @Transactional
    public User registerNewGroup(AddGroupDto addDto) {

          if (addDto.getIdSchool() == null && addDto.getNameSchool() == null)
            throw new ParcomException("school.can_not_be_null");

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

        userService.registerInSecurity(userCreateDto);
        return user;
    }



}
