package com.lcm.statistic_service.strategy;

import com.lcm.statistic_service.model.CourseRevenue;
import com.lcm.statistic_service.model.RevenueStatistic;

import java.util.List;

public interface RevenueStrategy {
    public List<RevenueStatistic> calculateRevenue();
    public List<CourseRevenue> getCourseRevenue(String period);
}
