package com.lcm.enrollment_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class EnrollmentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID courseId;
    private boolean paid;
    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    @JsonBackReference
    private Enrollment enrollment;

    public EnrollmentCourse(UUID id, UUID courseId, boolean paid, Enrollment enrollment) {
        this.id = id;
        this.courseId = courseId;
        this.paid = paid;
        this.enrollment = enrollment;
    }

    public EnrollmentCourse(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }
}
