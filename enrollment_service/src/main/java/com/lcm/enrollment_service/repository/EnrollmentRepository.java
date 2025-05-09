package com.lcm.enrollment_service.repository;

import com.lcm.enrollment_service.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findByStudentId(UUID studentId);
    List<Enrollment> findByStudentIdAndPaidFalse(UUID studentId);
    List<Enrollment> findByPaidTrue();
}
