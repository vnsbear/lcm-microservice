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
public class QuarterlyRevenueStrategy implements RevenueStrategy {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<RevenueStatistic> calculateRevenue() {
        try {
            ResponseEntity<RevenueStatistic[]> response = restTemplate.getForEntity(
                    "http://localhost:9090/enrollments/quarterly-revenue",
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
        String[] parts = period.split("-");
        int year = Integer.parseInt(parts[0]);
        String quarterStr = parts[1];
        int quarter = Integer.parseInt(quarterStr.substring(1)); // Q2 -> 2

        LocalDateTime startDate = getFirstDayOfQuarter(year, quarter);
        LocalDateTime endDate = getLastDayOfQuarter(year, quarter);
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

    private LocalDateTime getFirstDayOfQuarter(int year, int quarter) {
        int firstMonthOfQuarter = (quarter - 1) * 3 + 1;
        return LocalDate.of(year, firstMonthOfQuarter, 1).atStartOfDay();
    }

    private LocalDateTime getLastDayOfQuarter(int year, int quarter) {
        int firstMonthOfQuarter = (quarter - 1) * 3 + 1;
        return LocalDate.of(year, firstMonthOfQuarter + 2, 1)
                .plusMonths(1)
                .minusDays(1)
                .atTime(LocalTime.MAX);
    }
}
