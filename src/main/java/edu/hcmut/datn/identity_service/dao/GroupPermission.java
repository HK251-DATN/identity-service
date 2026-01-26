package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_permission")
public class GroupPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grp_per_id")
    private long groupPerId;
    
    @Column(name = "group_id")
    private Long groupId;
    
    @Column(name = "per_id")
    private Long perId;
    
    @Column(name = "is_active")
    private boolean isActive;
    
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    
    @Column(name = "granted_at")
    private LocalDateTime grantedAt;
    
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
