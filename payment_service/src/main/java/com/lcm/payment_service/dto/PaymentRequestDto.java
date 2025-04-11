package com.lcm.payment_service.dto;

import java.util.UUID;

public class PaymentRequestDto {
    private UUID enrollmentId;
    private UUID studentId;

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }
}
