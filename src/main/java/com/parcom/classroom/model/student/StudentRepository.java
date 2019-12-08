package com.parcom.classroom.model.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("select student from Student student " +
            "where student.group.id =  :idGroup " +
            "order by student.familyName" )

    List<Student> getStudents(@Param("idGroup") Long idGroup);

}
