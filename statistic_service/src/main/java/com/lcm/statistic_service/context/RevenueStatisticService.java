package com.lcm.statistic_service.context;

import com.lcm.statistic_service.model.CourseRevenue;
import com.lcm.statistic_service.model.RevenueStatistic;
import com.lcm.statistic_service.strategy.MonthlyRevenueStrategy;
import com.lcm.statistic_service.strategy.QuarterlyRevenueStrategy;
import com.lcm.statistic_service.strategy.RevenueStrategy;
import com.lcm.statistic_service.strategy.YearlyRevenueStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RevenueStatisticService {
    private final Map<String, RevenueStrategy> strategies = new HashMap<>();

    @Autowired
    public RevenueStatisticService(
            MonthlyRevenueStrategy monthlyStrategy,
            QuarterlyRevenueStrategy quarterlyStrategy,
            YearlyRevenueStrategy yearlyStrategy) {

        strategies.put("monthly", monthlyStrategy);
        strategies.put("quarterly", quarterlyStrategy);
        strategies.put("yearly", yearlyStrategy);
    }

    public List<RevenueStatistic> getRevenue(String revenueType) {
        RevenueStrategy strategy = strategies.get(revenueType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported period type: " + revenueType);
        }
        return strategy.calculateRevenue();
    }

    public List<CourseRevenue> getListCourseRevenue(String revenueType, String period) {
        RevenueStrategy strategy = strategies.get(revenueType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported period type: " + revenueType);
        }
        return strategy.getCourseRevenue(period);
    }
}
