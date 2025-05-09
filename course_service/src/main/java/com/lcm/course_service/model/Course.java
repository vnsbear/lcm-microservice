package com.lcm.course_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private String duration;
    private int fee;
    private String image;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @ManyToOne()
    @JoinColumn(name = "course_type_id")
    private CourseType courseType;

    @PrePersist()
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate()
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    public Course(UUID id, String name, String description, String duration, int fee, String image, LocalDateTime createAt, LocalDateTime updateAt, CourseType courseType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.fee = fee;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.courseType = courseType;
    }

    public Course(String name, String description, String duration, String image, int fee, CourseType courseType) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.image = image;
        this.fee = fee;
        this.courseType = courseType;
    }

    public Course(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
