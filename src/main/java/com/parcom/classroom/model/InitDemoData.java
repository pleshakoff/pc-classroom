package com.parcom.classroom.model;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupRepository;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolRepository;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentRepository;
import com.parcom.classroom.model.add.AddService;
import lombok.extern.java.Log;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@Log
public class InitDemoData {

    private final SchoolRepository schoolRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;



    public InitDemoData(SchoolRepository schoolRepository, GroupRepository groupRepository, StudentRepository studentRepository, AddService addService) {
        this.schoolRepository = schoolRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    private void run() {

        if (schoolRepository.count() > 0)
            return;
        log.info("Insert school");
        School school = School.builder().name("Лицей № 5 имени Бертольда Шварца").build();
        schoolRepository.save(school);
        log.info("Insert group");
        Group group = Group.builder().name("Пятый класс").school(school).build();
        groupRepository.save(group);

        Student student1 = Student.builder().firstName("Вася").familyName("Васин").group(group).build();
        log.info("Insert student 1");
        studentRepository.save(student1);
        Student student2 = Student.builder().firstName("Петя").familyName("Петин").group(group).build();
        log.info("Insert student 2");
        studentRepository.save(student2);

//        registerService.registerByGroup(new AddMemberDto("admin",
//                "Антон",
//                "Викторович",
//                "Плешаков",
//                "pleshakoff@gmail.com", "123",
//                "00000", "00000", null,group.getId()));
//
//
//        registerService.registerByStudent(new AddParentDto("parent",
//                "Иванов",
//                "Иван",
//                "Иванович",
//                "ivanov@gmail.com", "1234",
//                "00000", "00000", student2.getId()));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        run();
    }
}
