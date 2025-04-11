package com.lcm.enrollment_service.dto;

import java.util.List;
import java.util.UUID;

public class EnrollmentRequestDto {
    private UUID studentId;
    private List<UUID> courseIds;

    public EnrollmentRequestDto(List<UUID> courseIds, UUID studentId) {
        this.courseIds = courseIds;
        this.studentId = studentId;
    }

    public EnrollmentRequestDto() {}

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public List<UUID> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<UUID> courseIds) {
        this.courseIds = courseIds;
    }
}
