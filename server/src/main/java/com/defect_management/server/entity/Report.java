package com.defect_management.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reports")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    @Column(updatable = false)
    private LocalDateTime generatedAt = LocalDateTime.now();

    private String filePath;

    public enum Type {
        CSV, EXCEL, ANALYTICS
    }
}