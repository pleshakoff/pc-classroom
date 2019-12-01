package com.parcom.classroom.model.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/students",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping
    @ApiOperation(value = "Get current students")
    public List<Student> getCurrentStudents()  {
       return studentService.getCurrentStudents();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get current student by ID")
    public Student getCurrentStudent(@PathVariable Long id)  {
        return studentService.getCurrentStudent(id);
    }


    @GetMapping("/all")
    @ApiOperation(value = "Get all students in group")
    public List<Student> getAllStudents()
    {
        return studentService.getStudents();
    }



}
