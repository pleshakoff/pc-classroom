package com.parcom.classroom.services.add;

import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.classroom.model.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AddServiceImplTestConfiguration {

    @Bean
    AddService addService(GroupService groupService, StudentService studentService,
                          SchoolService schoolService, UserService userService
    ) {
        return new AddServiceImpl(groupService, studentService, schoolService, userService);
    }
}
