package com.lcm.enrollment_service.repository;

import com.lcm.enrollment_service.model.EnrollmentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EnrollmentCourseRepository extends JpaRepository<EnrollmentCourse, UUID> {
    List<EnrollmentCourse> findByEnrollmentId(UUID enrollmentId);
    List<EnrollmentCourse> findByPaidTrue();
    List<EnrollmentCourse> findByPaidTrueAndEnrollment_EnrollmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
