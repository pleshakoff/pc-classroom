package com.parcom.classroom.model.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/students",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping
    @ApiOperation(value = "Get my students")
    public List<Student> getMyStudents()  {
       return studentService.getMyStudents();
    }


    @GetMapping("/all")
    @ApiOperation(value = "Get all students in group")
    public List<Student> getAllStudents()
    {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get student by ID")
    public Student getMyStudent(@PathVariable Long id)  {
        return studentService.getMyStudent(id);
    }



    @PostMapping
    @ApiOperation(value = "Create student")
    public Student create(@Valid @RequestBody StudentDto studentDto,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return studentService.create(studentDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update student")
    public Student update(@PathVariable Long id,@Valid @RequestBody StudentDto studentDto,
                          BindingResult bindingResult) throws BindException
    {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return studentService.update(id,studentDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete student")
    public void delete(@PathVariable Long id)
    {
        studentService.delete(id);
    }




}
