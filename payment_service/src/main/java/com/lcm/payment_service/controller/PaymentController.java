package com.lcm.payment_service.controller;

import com.lcm.payment_service.dto.ApiResponse;
import com.lcm.payment_service.dto.PaymentRequestDto;
import com.lcm.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> pay(@RequestBody PaymentRequestDto request) {
        ApiResponse<Map<String, Object>> response = service.pay(request);
        return ResponseEntity.ok(response);
    }
}
