package com.lcm.enrollment_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID studentId;
    private boolean paid;
    private int totalCourse;
    private int totalAmount;
    private LocalDateTime enrollmentDate;

    public Enrollment(UUID id, UUID studentId, boolean paid, int totalCourse, int totalAmount, LocalDateTime enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.paid = paid;
        this.totalCourse = totalCourse;
        this.totalAmount = totalAmount;
        this.enrollmentDate = enrollmentDate;
    }

    public Enrollment() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(int totalCourse) {
        this.totalCourse = totalCourse;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
