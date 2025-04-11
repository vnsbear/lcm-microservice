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
    private LocalDateTime enrollmentDate;
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EnrollmentCourse> courses;

    public Enrollment(UUID id, UUID studentId, boolean paid, LocalDateTime enrollmentDate, List<EnrollmentCourse> courses) {
        this.id = id;
        this.studentId = studentId;
        this.paid = paid;
        this.enrollmentDate = enrollmentDate;
        this.courses = courses;
    }

    public Enrollment(){}

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

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public List<EnrollmentCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<EnrollmentCourse> courses) {
        this.courses = courses;
    }
}
