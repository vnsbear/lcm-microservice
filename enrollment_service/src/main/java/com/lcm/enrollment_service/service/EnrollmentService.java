package com.lcm.enrollment_service.service;

import com.lcm.enrollment_service.dto.APIResponseDto;
import com.lcm.enrollment_service.model.Enrollment;
import com.lcm.enrollment_service.model.EnrollmentCourse;
import com.lcm.enrollment_service.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EnrollmentService {
    @Autowired private EnrollmentRepository repo;

    public APIResponseDto<Enrollment> register(UUID studentId, List<UUID> courseIds) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setPaid(false);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        List<EnrollmentCourse> ecList = new ArrayList<>();
        for (UUID courseId : courseIds) {
            EnrollmentCourse ec = new EnrollmentCourse();
            ec.setCourseId(courseId);
            ec.setEnrollment(enrollment);
            ecList.add(ec);
        }

        enrollment.setCourses(ecList);
        Enrollment saved = repo.save(enrollment);

        return new APIResponseDto<>(201, "Enrollment created successfully", saved);
    }

    public APIResponseDto<List<Enrollment>> getUnpaidByStudent(UUID studentId) {
        List<Enrollment> list = repo.findByStudentIdAndPaidFalse(studentId);
        return new APIResponseDto<>(HttpStatus.OK.value(), "Unpaid enrollments fetched", list);
    }

    public APIResponseDto<List<Enrollment>> getByStudent(UUID studentId) {
        List<Enrollment> list = repo.findByStudentId(studentId);
        return new APIResponseDto<>(HttpStatus.OK.value(), "All enrollments fetched", list);
    }

    public APIResponseDto<String> markAsPaid(UUID studentId) {
        List<Enrollment> unpaid = repo.findByStudentIdAndPaidFalse(studentId);
        for (Enrollment e : unpaid) {
            e.setPaid(true);
            repo.save(e);
        }
        return new APIResponseDto<>(HttpStatus.OK.value(), "Marked as paid", "Marked " + unpaid.size() + " enrollments");
    }

    public APIResponseDto<Enrollment> getById(UUID id) {
        Optional<Enrollment> optional = repo.findById(id);
        System.out.println("Enrollment fetched: " + optional);

        if (optional.isPresent()) {
            return new APIResponseDto<>(HttpStatus.OK.value(), "Enrollment fetched", optional.get());
        } else {
            return new APIResponseDto<>(HttpStatus.NOT_FOUND.value(), "Enrollment not found", null);
        }
    }

}
