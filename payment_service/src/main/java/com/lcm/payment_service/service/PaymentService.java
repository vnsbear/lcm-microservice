package com.lcm.payment_service.service;

import com.lcm.payment_service.dto.PaymentRequestDto;
import com.lcm.payment_service.dto.ApiResponse;
import com.lcm.payment_service.model.Payment;
import com.lcm.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ApiResponse<Map<String, Object>> pay(PaymentRequestDto request) {
        // Lấy thông tin enrollment theo enrollmentId
        LinkedHashMap response = restTemplate.getForObject(
                "http://localhost:8080/enrollments/" + request.getEnrollmentId(),
                LinkedHashMap.class
        );

        LinkedHashMap enrollment = (LinkedHashMap) response.get("data");

        if (enrollment == null || enrollment.get("courses") == null) {
            return new ApiResponse<>("error", "Enrollment or courses not found", null);
        }

        List<LinkedHashMap<String, Object>> courses = (List<LinkedHashMap<String, Object>>) enrollment.get("courses");

        int total = 0;
        List<Map<String, Object>> courseSummaries = new ArrayList<>();

        for (LinkedHashMap<String, Object> course : courses) {
            String courseId = (String) course.get("courseId");

            System.out.println("Fetching course info for ID: " + courseId);

            // Gọi course-service
            LinkedHashMap outerResponse = restTemplate.getForObject(
                    "http://localhost:8080/courses/" + courseId,
                    LinkedHashMap.class
            );

            LinkedHashMap courseResponse = outerResponse != null ? (LinkedHashMap) outerResponse.get("data") : null;

            System.out.println("Course response: " + courseResponse);

            String courseName = courseResponse != null ? (String) courseResponse.get("name") : null; // Đúng key ở đây!
            Object feeObj = courseResponse != null ? courseResponse.get("fee") : 0;

            int fee = 0;
            if (feeObj instanceof Integer) {
                fee = (int) feeObj;
            } else if (feeObj instanceof Double) {
                fee = (int) ((Double) feeObj).doubleValue();
            }

            total += fee;

            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("courseId", courseId);
            courseInfo.put("courseName", courseName);
            courseInfo.put("fee", fee);
            courseSummaries.add(courseInfo);
        }


        // Tạo bản ghi thanh toán
        Payment payment = new Payment();
        payment.setStudentId(request.getStudentId());
        payment.setEnrollmentId(request.getEnrollmentId());
        payment.setAmount(total);
        payment.setTimestamp(LocalDateTime.now());
        repo.save(payment);

        Map<String, Object> data = new HashMap<>();
        data.put("totalPaid", total);
        data.put("coursesPaid", courseSummaries);

        return new ApiResponse<>("success", "Payment processed successfully", data);
    }
}
