package edu.hcmut.datn.identity_service.service;

import edu.hcmut.datn.identity_service.dao.Permission;

import java.util.List;

public interface PermissionService {
    Permission create();
    
    Permission get();
    
    List<Permission> getAll();
    
    Permission update();
    
    boolean delete();

}
