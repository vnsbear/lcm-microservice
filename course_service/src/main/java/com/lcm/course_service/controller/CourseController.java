package com.lcm.course_service.controller;

import com.lcm.course_service.model.Course;
import com.lcm.course_service.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    private CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return courseService.create(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable String id) {
        return courseService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Course course) {
        UUID uuid = UUID.fromString(id);
        return courseService.update(uuid, course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return courseService.delete(uuid);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String uploadDir = "C:/Users/Son/Desktop/lcm-microservice/course_service/uploads/images";

            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename().replaceAll("\\s+", "_");

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean dirsCreated = dir.mkdirs();
                if (!dirsCreated) {
                    throw new Exception("Không thể tạo thư mục " + uploadDir);
                }
            }

            File saveFile = new File(uploadDir + "/" + fileName);
            file.transferTo(saveFile);

            String imagePath = "/uploads/images/" + fileName;

            return ResponseEntity.ok().body(Map.of("status", "success", "imagePath", imagePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }




}