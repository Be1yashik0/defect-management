package com.defect_management.server.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Project_Users")
@Data
public class ProjectUser {
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String roleInProject;
}