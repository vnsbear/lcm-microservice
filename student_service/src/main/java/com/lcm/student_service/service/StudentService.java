package com.lcm.student_service.service;

import com.lcm.student_service.dto.APIResponseDto;
import com.lcm.student_service.model.Student;
import com.lcm.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public APIResponseDto create(Student student) {
        Student saved = repo.save(student);
        return new APIResponseDto("success", "Student created successfully", saved);
    }

    public APIResponseDto get(UUID id) {
        Optional<Student> found = repo.findById(id);
        if (found.isPresent()) {
            return new APIResponseDto("success", "Student found", found.get());
        } else {
            return new APIResponseDto("error", "Student not found", null);
        }
    }

    public APIResponseDto getAll() {
        List<Student> list = repo.findAll();
        return new APIResponseDto("success", "List of students", list);
    }
}
