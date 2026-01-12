package edu.hcmut.datn.identity_service.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long perId;
    
    @Column(unique = true)
    private String perCode;
    
    @Column
    private String perName;
    
    @Column
    private String perDescription;
    
    @Column
    private boolean isActive;
    
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
