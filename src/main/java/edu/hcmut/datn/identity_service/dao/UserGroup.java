package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_group")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    private long userGroupId;
    
    @Column(name = "user_id")
    private long userId;
    
    @Column(name = "group_id")
    private long groupId;
    
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
