package edu.hcmut.datn.identity_service.controller;

import edu.hcmut.datn.identity_service.dao.Group;
import edu.hcmut.datn.identity_service.dto.request.GroupRequest;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    public ResponseEntity<ApiResponse<Group>> create(@RequestBody GroupRequest groupRequest) {
        return null;
    }
    
    public ResponseEntity<ApiResponse<List<Group>>> getAll() {
        return null;
    }
    
    public ResponseEntity<ApiResponse<Group>> getGroup(@PathVariable Long groupId) {
        return null;
    }
    
    public ResponseEntity<ApiResponse<Group>> update(@PathVariable Long groupId, @RequestBody GroupRequest groupRequest) {
        return null;
    }
    
    public ResponseEntity<ApiResponse<Group>> delete(@PathVariable Long groupId) {
        return null;
    }
}
