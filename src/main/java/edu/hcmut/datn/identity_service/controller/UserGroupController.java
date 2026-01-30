package edu.hcmut.datn.identity_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import edu.hcmut.datn.identity_service.dao.UserGroup;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import edu.hcmut.datn.identity_service.service.UserGroupService;

public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;

    public ResponseEntity<ApiResponse<UserGroup>> create() {
        return null;
    }

    public ResponseEntity<ApiResponse<UserGroup>> get() {
        return null;
    }

    public ResponseEntity<ApiResponse<UserGroup>> update() {
        return null;
    }

    public ResponseEntity<ApiResponse<UserGroup>> delete() {
        return null;
    }

}
