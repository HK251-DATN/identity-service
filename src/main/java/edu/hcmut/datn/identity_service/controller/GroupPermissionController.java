package edu.hcmut.datn.identity_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hcmut.datn.identity_service.dao.GroupPermission;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import edu.hcmut.datn.identity_service.service.GroupPermissionService;

@RestController
@RequestMapping("/api/permission-group")
public class GroupPermissionController {

    @Autowired
    GroupPermissionService groupPermissionService;

    public ResponseEntity<ApiResponse<GroupPermission>> create() {
        return null;
    }

    public ResponseEntity<ApiResponse<GroupPermission>> get() {
        return null;
    }

    public ResponseEntity<ApiResponse<GroupPermission>> update() {
        return null;
    }

    public ResponseEntity<ApiResponse<GroupPermission>> delete() {
        return null;
    }
}
