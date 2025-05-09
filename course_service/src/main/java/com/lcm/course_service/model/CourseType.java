package com.lcm.course_service.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CourseType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    public CourseType(UUID id, String name, Language language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }

    public CourseType(String name, Language language) {
        this.name = name;
        this.language = language;
    }

    public CourseType(){}

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
