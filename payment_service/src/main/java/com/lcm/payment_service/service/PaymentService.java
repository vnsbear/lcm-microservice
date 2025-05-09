package com.lcm.payment_service.service;

import com.lcm.payment_service.dto.PaymentRequestDto;
import com.lcm.payment_service.model.Payment;
import com.lcm.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Map<String, Object>> pay(PaymentRequestDto request) {
        // Fetch enrollment details
        LinkedHashMap response = restTemplate.getForObject(
                "http://localhost:9090/enrollments/" + request.getEnrollmentId(),
                LinkedHashMap.class
        );

        System.out.println(response);

        if (response == null ) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Enrollment or courses not found");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Integer amount = (Integer) response.get("totalAmount");


        Payment payment = new Payment();
        payment.setStudentId(request.getStudentId());
        payment.setEnrollmentId(request.getEnrollmentId());
        payment.setAmount(amount);
        payment.setPaymentMethod("Banking");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(request.getMethod());
        payment.setStatus("success");
        repo.save(payment);

        try {
            restTemplate.put("http://localhost:9090/enrollments/mark-paid/" + request.getEnrollmentId(), null);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "partial_success");
            errorResponse.put("message", "Payment succeeded but failed to mark enrollment as paid");
            return ResponseEntity.ok(errorResponse);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("paymentId", payment.getId());
        data.put("amount", amount);

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        successResponse.put("message", "Payment processed and enrollment marked as paid");
        successResponse.put("data", data);

        return ResponseEntity.ok(successResponse);
    }
}
