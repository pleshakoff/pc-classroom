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


    @PostMapping
    @ApiOperation(value = "Create student")
    public Student create(@Valid @RequestBody StudentDTO studentDTO,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return studentService.create(studentDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update student")
    public Student delete(@PathVariable Long id,@Valid @RequestBody StudentDTO studentDTO,
                          BindingResult bindingResult) throws BindException
    {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return studentService.update(id,studentDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Update student")
    public void delete(@PathVariable Long id)
    {
        studentService.delete(id);
    }




}
