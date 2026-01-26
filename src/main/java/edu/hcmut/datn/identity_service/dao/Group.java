package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupId;
    
    @Column(name = "group_name")
    private String groupName;
    
    @Column(name = "group_des")
    private String description;
    
    @Column(name = "is_active")
    private boolean isActive;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist () {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate () {
        updatedAt = LocalDateTime.now();
    }
    
}
