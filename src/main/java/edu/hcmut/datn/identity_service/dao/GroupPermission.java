package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class GroupPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long groupPerId;
    
    @Column
    private Long groupId;
    
    @Column
    private Long perId;
    
    @Column
    private LocalDateTime validUntil;
    
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updatedAt")
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
