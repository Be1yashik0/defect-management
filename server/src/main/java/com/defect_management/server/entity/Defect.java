package com.defect_management.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Defects")
@Data
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defectId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDate deadline;

    private String attachmentPath;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private String buildingSection;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public enum Status {
        NEW, IN_PROGRESS, UNDER_REVIEW, CLOSED, CANCELED
    }

    public enum Severity {
        MINOR, MAJOR, CRITICAL
    }
}
