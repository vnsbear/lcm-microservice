package com.lcm.payment_service.dto;

import java.util.List;
import java.util.UUID;

public class PaymentResponseDto {
    private UUID paymentId;
    private int totalAmount;
    private List<String> courseNames;

    public PaymentResponseDto() {}

    public PaymentResponseDto(UUID paymentId, int totalAmount, List<String> courseNames) {
        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
        this.courseNames = courseNames;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(List<String> courseNames) {
        this.courseNames = courseNames;
    }
}
