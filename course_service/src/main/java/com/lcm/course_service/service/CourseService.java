package com.lcm.course_service.service;

import com.lcm.course_service.dto.APIResponseDto;
import com.lcm.course_service.model.Course;
import com.lcm.course_service.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    @Autowired private CourseRepository repo;

    public APIResponseDto<List<Course>> getAll() {
        List<Course> courses = repo.findAll();
        return new APIResponseDto<>(200, "List of all courses", courses);
    }

    public APIResponseDto<Course> getById(String id){
        Optional<Course> course = repo.findById(UUID.fromString(id));
        return course
                .map(c -> new APIResponseDto<>(200, "Course found", c))
                .orElseGet(() -> new APIResponseDto<>(404, "Course not found", null));
    }

    public APIResponseDto<Course> createCourse(Course course) {
        Course saved = repo.save(course);
        return new APIResponseDto<>(200, "Course created successfully", saved);
    }

    public APIResponseDto<String> deleteCourse(UUID id) {
        try {
            repo.deleteById(id);
            return new APIResponseDto<>(200, "Course deleted", id.toString());
        } catch (Exception e) {
            return new APIResponseDto<>(404, "Course not found or delete failed", null);
        }
    }
}
