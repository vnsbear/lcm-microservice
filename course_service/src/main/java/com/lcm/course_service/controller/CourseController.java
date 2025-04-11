package com.lcm.course_service.controller;

import com.lcm.course_service.model.Course;
import com.lcm.course_service.service.CourseService;
import com.lcm.course_service.dto.APIResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<APIResponseDto<Course>> create(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @GetMapping
    public ResponseEntity<APIResponseDto<List<Course>>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponseDto<Course>> getById(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponseDto<String>> delete(@PathVariable String id) {
        return ResponseEntity.ok(courseService.deleteCourse(UUID.fromString(id)));
    }
}
