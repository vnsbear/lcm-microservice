package com.lcm.statistic_service.model;

public class RevenueStatistic {
    public int totalAmount;
    public String period;

    public RevenueStatistic(String period, int totalAmount) {
        this.totalAmount = totalAmount;
        this.period = period;
    }

    public RevenueStatistic(){}

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
