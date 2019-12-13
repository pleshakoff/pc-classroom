package com.parcom.classroom.model.student;

import com.parcom.classroom.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentToUserRepository extends CrudRepository<StudentToUser, Long> {

    @Query("select su.student from StudentToUser su " +
            "where su.user.id =  :idUser " +
            "   and su.student.group.id =  :idGroup " +
            "order by su.student.familyName" )
    List<Student> getCurrentStudents(@Param("idUser") Long idUser, @Param("idGroup") Long idGroup);

    void deleteAllByUser(User user);
    void deleteAllByStudent(Student student);



}
