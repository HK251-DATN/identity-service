package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id")
    private long perId;
    
    @Column(name = "per_code")
    @Setter
    private String perCode;
    
    @Column(name = "per_name")
    @Setter
    private String perName;
    
    @Column(name = "per_des")
    @Setter
    private String perDescription;
    
    @Column(name = "is_active")
    @Setter
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
