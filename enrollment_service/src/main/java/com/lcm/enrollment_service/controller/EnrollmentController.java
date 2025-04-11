package com.lcm.enrollment_service.controller;

import com.lcm.enrollment_service.dto.APIResponseDto;
import com.lcm.enrollment_service.dto.EnrollmentRequestDto;
import com.lcm.enrollment_service.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired private EnrollmentService service;

    @PostMapping
    public APIResponseDto<?> register(@RequestBody EnrollmentRequestDto request) {
        return service.register(request.getStudentId(), request.getCourseIds());
    }

    @GetMapping("/student/{id}")
    public APIResponseDto<?> getByStudent(@PathVariable UUID id) {
        return service.getByStudent(id);
    }

    @GetMapping("/student/{id}/unpaid")
    public APIResponseDto<?> getUnpaidByStudent(@PathVariable UUID id) {
        return service.getUnpaidByStudent(id);
    }

    @PutMapping("/mark-paid/{studentId}")
    public APIResponseDto<?> markAsPaid(@PathVariable UUID studentId) {
        return service.markAsPaid(studentId);
    }

    @GetMapping("/{id}")
    public APIResponseDto<?> getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}
