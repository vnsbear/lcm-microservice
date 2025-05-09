package com.lcm.course_service.service;

import com.lcm.course_service.model.Course;
import com.lcm.course_service.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    @Autowired
    private CourseRepository repo;


    public ResponseEntity<Course> create(Course course) {
        try {
            Course saved = repo.save(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public List<Course> getAll() {
        return repo.findAll();
    }

    public ResponseEntity<Course> getById(String id) {
        Optional<Course> course = repo.findById(UUID.fromString(id));
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<String> update(UUID id, Course updatedCourse) {
        Optional<Course> optionalCourse = repo.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();

            existingCourse.setName(updatedCourse.getName());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setDuration(updatedCourse.getDuration());
            existingCourse.setFee(updatedCourse.getFee());
            existingCourse.setImage(updatedCourse.getImage());

            Course savedCourse = repo.save(existingCourse);
            return ResponseEntity.ok("Update course successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }


    public ResponseEntity<String> delete(UUID id) {
        try {
            repo.deleteById(id);
            return ResponseEntity.ok("Course deleted successfully!");  // HTTP 200 on successful deletion
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found or delete failed");  // HTTP 404 if delete failed
        }
    }
}
