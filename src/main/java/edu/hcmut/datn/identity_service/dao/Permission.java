package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id")
    private long perId;
    
    @Column(name = "per_code")
    private String perCode;
    
    @Column(name = "per_name")
    private String perName;
    
    @Column(name = "per_des")
    private String perDescription;
    
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
