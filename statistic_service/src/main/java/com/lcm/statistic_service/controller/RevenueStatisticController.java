package com.lcm.statistic_service.controller;

import com.lcm.statistic_service.context.RevenueStatisticService;
import com.lcm.statistic_service.model.CourseRevenue;
import com.lcm.statistic_service.model.RevenueStatistic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/statistics/revenue")
public class RevenueStatisticController {
    private RevenueStatisticService revenueService;

    @Autowired
    public RevenueStatisticController(RevenueStatisticService revenueService) {
        this.revenueService = revenueService;
    }

    @GetMapping("/{revenueType}")
    public ResponseEntity<List<RevenueStatistic>> getRevenue(
            @PathVariable String revenueType) {
        List<RevenueStatistic> statistics = revenueService.getRevenue(revenueType);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/{revenueType}/details")
    public ResponseEntity<List<CourseRevenue>> getListCourseRevenue(
            @PathVariable String revenueType,
            @RequestParam String period) {
        List<CourseRevenue> courseRevenues = revenueService.getListCourseRevenue(revenueType, period);
        return ResponseEntity.ok(courseRevenues);
    }
}
