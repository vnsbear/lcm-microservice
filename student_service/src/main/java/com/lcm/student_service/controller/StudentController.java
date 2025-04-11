package com.lcm.student_service.controller;

import com.lcm.student_service.dto.APIResponseDto;
import com.lcm.student_service.model.Student;
import com.lcm.student_service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @PostMapping
    public APIResponseDto create(@RequestBody Student student) {
        return service.create(student);
    }

    @GetMapping("/{id}")
    public APIResponseDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public APIResponseDto getAll() {
        return service.getAll();
    }
}
