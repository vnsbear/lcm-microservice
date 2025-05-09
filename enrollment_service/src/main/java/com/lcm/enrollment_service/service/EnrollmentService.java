package com.lcm.enrollment_service.service;

import com.lcm.enrollment_service.model.Enrollment;
import com.lcm.enrollment_service.model.EnrollmentCourse;
import com.lcm.enrollment_service.repository.EnrollmentCourseRepository;
import com.lcm.enrollment_service.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    @Autowired private EnrollmentRepository eRepo;
    @Autowired private EnrollmentCourseRepository ecRepo;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Enrollment> enrollment(UUID studentId, List<UUID> courseIds) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setPaid(false);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setTotalCourse(courseIds.size());
        int totalAmount = 0;
        try {
            for (UUID courseId : courseIds) {
                ResponseEntity<Map> response = restTemplate.getForEntity(
                        "http://localhost:9090/courses/" + courseId, Map.class);
                if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                    throw new IllegalArgumentException("Course not found: " + courseId);
                }

                Map<String, Object> courseResponse = response.getBody();
                if (courseResponse == null) {
                    throw new IllegalArgumentException("Invalid course data for: " + courseId);
                }

                int fee = (Integer) courseResponse.get("fee");
                totalAmount += fee;
            }
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        enrollment.setTotalAmount(totalAmount);

        Enrollment saved = eRepo.save(enrollment);

        for (UUID courseId : courseIds) {
            EnrollmentCourse ec = new EnrollmentCourse();
            ec.setCourseId(courseId);
            ec.setEnrollment(enrollment);
            ecRepo.save(ec);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<String> markAsPaid(UUID enrollmentId) {
        Optional<Enrollment> optionalEnrollment = eRepo.findById(enrollmentId);
        if (optionalEnrollment.isEmpty()) {
            return ResponseEntity.status(404).body("Enrollment not found");
        }

        Enrollment enrollment = optionalEnrollment.get();
        enrollment.setPaid(true);
        eRepo.save(enrollment);

        List<EnrollmentCourse> enrollmentCourses = ecRepo.findByEnrollmentId(enrollmentId);
        for (EnrollmentCourse ec : enrollmentCourses) {
            ec.setPaid(true);
            ecRepo.save(ec);
        }

        return ResponseEntity.ok("Enrollment and related courses marked as paid");
    }


    public ResponseEntity<Enrollment> getById(UUID id) {
        Optional<Enrollment> optional = eRepo.findById(id);
        System.out.println("Enrollment fetched: " + optional);

        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Enrollment enrollment = optional.get();
        return ResponseEntity.ok(enrollment);
    }

    public ResponseEntity<List<Map<String, Object>>> getMonthlyRevenue() {
        // Lấy các bản ghi đã thanh toán
        List<Enrollment> paidEnrollments = eRepo.findByPaidTrue();

        // Tạo map để nhóm theo yyyy-MM và tính tổng
        Map<String, Integer> monthlyRevenue = new HashMap<>();

        for (Enrollment enrollment : paidEnrollments) {
            LocalDateTime date = enrollment.getEnrollmentDate();
            String period = YearMonth.of(date.getYear(), date.getMonth())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM"));
            int amount = enrollment.getTotalAmount();

            // Cộng dồn vào map
            monthlyRevenue.put(period,
                    monthlyRevenue.getOrDefault(period, 0) + amount);
            // 2025-04
            // totalAmout: 123
        }

        // Chuyển từ Map<String, Integer> sang List<Map<String, Object>>
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : monthlyRevenue.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("period", entry.getKey());
            item.put("totalAmount", entry.getValue());
            result.add(item);
        }

        // Sắp xếp theo thời gian tăng dần
        result.sort(Comparator.comparing(map -> (String) map.get("period")));

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenue() {
        // Lấy các bản ghi đã thanh toán
        List<Enrollment> paidEnrollments = eRepo.findByPaidTrue();

        // Tạo map để nhóm theo yyyy-Qx và tính tổng
        Map<String, Integer> quarterlyRevenue = new HashMap<>();

        for (Enrollment enrollment : paidEnrollments) {
            LocalDateTime date = enrollment.getEnrollmentDate();
            int month = date.getMonthValue();
            int quarter = (month - 1) / 3 + 1;
            String period = date.getYear() + "-Q" + quarter;
            int amount = enrollment.getTotalAmount();

            // Cộng dồn vào map
            quarterlyRevenue.put(period,
                    quarterlyRevenue.getOrDefault(period, 0) + amount);
            // 2025-Q2
        }

        // Chuyển từ Map<String, Integer> sang List<Map<String, Object>>
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : quarterlyRevenue.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("period", entry.getKey());
            item.put("totalAmount", entry.getValue());
            result.add(item);
        }

        // Sắp xếp theo thời gian tăng dần
        result.sort(Comparator.comparing(map -> (String) map.get("period")));

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Map<String, Object>>> getYearlyRevenue() {
        // Lấy các bản ghi đã thanh toán
        List<Enrollment> paidEnrollments = eRepo.findByPaidTrue();

        // Tạo map để nhóm theo yyyy-Qx và tính tổng
        Map<String, Integer> yearlyRevenue  = new HashMap<>();

        for (Enrollment enrollment : paidEnrollments) {
            LocalDateTime date = enrollment.getEnrollmentDate();
            String period = String.valueOf(date.getYear());
            int amount = enrollment.getTotalAmount();

            // Cộng dồn vào map
            yearlyRevenue.put(period,
                    yearlyRevenue.getOrDefault(period, 0) + amount);
            // 2025
            // totalAmount
        }

        // Chuyển từ Map<String, Integer> sang List<Map<String, Object>>
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : yearlyRevenue .entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("period", entry.getKey());
            item.put("totalAmount", entry.getValue());
            result.add(item);
        }

        // Sắp xếp theo thời gian tăng dần
        result.sort(Comparator.comparing(map -> (String) map.get("period")));

        return ResponseEntity.ok(result);
    }


    public ResponseEntity<List<Map<String, Object>>> getCourseRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        // lay danh sach enrollmentcorse trong khoang thoi gian
        List<EnrollmentCourse> paidEnrollmentCourses;
        if (startDate != null && endDate != null) {
            paidEnrollmentCourses = ecRepo.findByPaidTrueAndEnrollment_EnrollmentDateBetween(startDate, endDate);
        } else {
            paidEnrollmentCourses = ecRepo.findByPaidTrue();
        }

        // lay danh sach theo id course
        Map<UUID, List<EnrollmentCourse>> courseGroups = new HashMap<>();
        for (EnrollmentCourse ec : paidEnrollmentCourses) {
            UUID courseId = ec.getCourseId();
            if (!courseGroups.containsKey(courseId)) {
                courseGroups.put(courseId, new ArrayList<>());
            }
            courseGroups.get(courseId).add(ec);
        }

        List<Map<String, Object>> result = new ArrayList<>();

        try {
            // Duyệt qua từng nhóm courseId
            for (Map.Entry<UUID, List<EnrollmentCourse>> entry : courseGroups.entrySet()) {
                UUID courseId = entry.getKey();
                List<EnrollmentCourse> enrollmentCourses = entry.getValue();

                // Gọi REST API để lấy thông tin khóa học
                ResponseEntity<Map> response = restTemplate.getForEntity(
                        "http://localhost:9090/courses/" + courseId, Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Map<String, Object> courseData = response.getBody();
                    System.out.println("COurse data get fee: " + courseData.get("fee"));
                    // Tính doanh thu của khóa học
                    int quantity = enrollmentCourses.size();
                    int fee = (Integer) courseData.get("fee");
                    int totalAmount = quantity * fee;

                    Map<String, Object> courseRevenue = new HashMap<>();
                    courseRevenue.put("courseName", courseData.get("name"));
                    courseRevenue.put("quantity", quantity);
                    courseRevenue.put("totalAmount", totalAmount);

                    result.add(courseRevenue);
                }
            }

            return ResponseEntity.ok(result);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

}
