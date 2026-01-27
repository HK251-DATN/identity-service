package edu.hcmut.datn.identity_service.controller;

import edu.hcmut.datn.identity_service.dao.Permission;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    
    public ResponseEntity<ApiResponse<Permission>> create() {
        return null;
    }
    
    public ResponseEntity<ApiResponse<Permission>> get() {
        return null;
    }
    
    public ResponseEntity<ApiResponse<List<Permission>>> getAll() {
        return null;
    }
    
    public ResponseEntity<ApiResponse<Permission>> update() {
        return null;
    }
    
    public ResponseEntity<ApiResponse<Permission>> delete() {
        return null;
    }
    
}
