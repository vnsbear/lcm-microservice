package com.lcm.statistic_service.model;

public class CourseRevenue {
    public String courseName;
    public int quantity;
    public int totalAmount;

    public CourseRevenue(String courseName, int quantity, int totalAmount) {
        this.courseName = courseName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public CourseRevenue(){}

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
