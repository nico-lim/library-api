package com.myrental.web.controller;

import com.myrental.web.dto.ApiResponse;
import com.myrental.web.dto.CreateStudentRequestBody;
import com.myrental.web.dto.UpdateStudentRequestBody;
import com.myrental.web.model.Student;
import com.myrental.web.repository.StudentRepository;
import com.myrental.web.service.StudentService;
import com.myrental.web.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ApiResponse all() {
        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                studentRepository.findAll()
        );
    }

    @PostMapping("")
    public ApiResponse create(@Valid @RequestBody CreateStudentRequestBody body) throws Exception {
        Student student = studentService.create(body);

        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                student
        );
    }

    @PutMapping("/{id}")
    public ApiResponse update(@Valid @RequestBody UpdateStudentRequestBody body, @PathVariable Long id) throws Exception {
        Student student = studentService.update(body, id);

        return ResponseHandler.generateResponse(
                "student updated",
                HttpStatus.OK,
                student
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) throws Exception {
        Student student = studentService.delete(id);

        return ResponseHandler.generateResponse(
                "student deleted",
                HttpStatus.OK,
                student
        );
    }

    @GetMapping("{id}")
    public ApiResponse read(@PathVariable Long id) {
        Student student = studentService.readWithQuerydsl(id);

        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                student
        );
    }
}
