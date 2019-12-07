package com.parcom.classroom.model.add;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.classroom.model.user.User;
import com.parcom.classroom.model.user.UserCreateDto;
import com.parcom.classroom.model.user.UserRemote;
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
    private final UserRemote userRemote;


    @Transactional
    public User registerByGroup(AddMemberDto addDto) {

        if (addDto.getIdGroup() == null) {
            throw new RuntimeException("Необходимо выбрать класс");
        }

        groupService.getById(addDto.getIdGroup());

        UserCreateDto userCreateDto = UserCreateDto.builder().
                email(addDto.getEmail()).
                password(addDto.getPassword()).
                role(UserUtils.ROLE_MEMBER).
                idGroup(addDto.getIdGroup()).build();

        User user = userRemote.register(userCreateDto);

        try {

            if (addDto.getIdStudent() != null) {
                Student student = studentService.getByOrNull(addDto.getIdStudent());
                studentService.linkStudentToUser(student,user.getId());
            }
        }
        catch (Exception e)
        {
            userRemote.delete(user.getId());
            throw e;
        }
        return user;

    }


    @Transactional
    User registerByStudent(AddParentDto addDto) {

        if (addDto.getIdStudent() == null) {
            throw new RuntimeException("Необходимо выбрать ученика");
        }
        Student student = studentService.getById(addDto.getIdStudent());


        UserCreateDto userCreateDto = UserCreateDto.builder().email(addDto.getEmail()).
                password(addDto.getPassword()).
                role(UserUtils.ROLE_PARENT).
                idGroup(student.getGroup().getId()).build();

        User user = userRemote.register(userCreateDto);

        try {
           studentService.linkStudentToUser(student,user.getId());
        }
        catch (Exception e)
        {
            userRemote.delete(user.getId());
            throw e;
        }
        return user;
    }

    @Transactional
    User registerNewGroup(AddGroupDto addDto) {

          if (addDto.getIdSchool() == null && addDto.getNameSchool() == null)
            throw new  RuntimeException("Необходимо выбрать школу или ввести наименование");

        School school = schoolService.getOrCreate(addDto.getIdSchool(),addDto.getNameSchool());
        Group group = groupService.create(addDto.getNameGroup(),school);

        UserCreateDto userCreateDto = UserCreateDto.builder().
                email(addDto.getEmail()).
                password(addDto.getPassword()).
                role(UserUtils.ROLE_ADMIN).
                idGroup(group.getId()).build();

        return userRemote.register(userCreateDto);
    }



}
