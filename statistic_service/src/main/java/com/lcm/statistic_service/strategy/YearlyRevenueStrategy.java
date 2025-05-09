package com.lcm.statistic_service.strategy;

import com.lcm.statistic_service.model.CourseRevenue;
import com.lcm.statistic_service.model.RevenueStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class YearlyRevenueStrategy implements RevenueStrategy {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<RevenueStatistic> calculateRevenue() {
        try {
            ResponseEntity<RevenueStatistic[]> response = restTemplate.getForEntity(
                    "http://localhost:9090/enrollments/yearly-revenue",
                    RevenueStatistic[].class
            );

            if (response.getBody() == null) {
                return Collections.emptyList();
            }

            return Arrays.asList(response.getBody());
        }
        catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CourseRevenue> getCourseRevenue(String period){
        int year = Integer.parseInt(period);

        LocalDateTime startDate = getFirstDayOfYear(year);
        LocalDateTime endDate = getLastDayOfYear(year);
        String date = "startDate=" + startDate + "&" + "endDate=" + endDate;
        try {
            ResponseEntity<CourseRevenue[]> response = restTemplate.getForEntity(
                    "http://localhost:9090/enrollments/course-revenue?" + date,
                    CourseRevenue[].class
            );
            System.out.println("Response: " + response.getBody());
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        }
        catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getFirstDayOfYear(int year) {
        return LocalDate.of(year, 1, 1).atStartOfDay();
    }

    private LocalDateTime getLastDayOfYear(int year) {
        return LocalDate.of(year, 12, 31).atTime(LocalTime.MAX);
    }
}
