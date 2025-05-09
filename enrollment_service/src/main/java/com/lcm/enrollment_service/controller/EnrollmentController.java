package com.lcm.enrollment_service.controller;

import com.lcm.enrollment_service.dto.EnrollmentRequestDto;
import com.lcm.enrollment_service.model.Enrollment;
import com.lcm.enrollment_service.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired private EnrollmentService service;

    @PostMapping
    public ResponseEntity<Enrollment> enrollment(@RequestBody Map<String, Object> request) {
        UUID studentId = UUID.fromString((String) request.get("studentId"));
        Object courseIdsObject = request.get("courseIds");
        List<String> courseIdStrings = (List<String>) courseIdsObject;
        System.out.println(courseIdStrings);
        List<UUID> courseIds = new ArrayList<>();
        for (String id : courseIdStrings) {
            courseIds.add(UUID.fromString(id));
        }
        return service.enrollment(studentId, courseIds);
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyRevenue() {
        return service.getMonthlyRevenue();
    }

    @GetMapping("/quarterly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenue() {
        return service.getQuarterlyRevenue();
    }

    @GetMapping("/yearly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getYearlyRevenue() {
        return service.getYearlyRevenue();
    }

    @GetMapping("/course-revenue")
    public ResponseEntity<List<Map<String, Object>>> getCourseRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return service.getCourseRevenue(startDate, endDate);
    }

    @PutMapping("/mark-paid/{enrollmentId}")
    public ResponseEntity<String> markAsPaid(@PathVariable UUID enrollmentId) {
        return service.markAsPaid(enrollmentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id);
    }

}
