package edu.hcmut.datn.identity_service.controller;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dto.request.UserRequest;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import edu.hcmut.datn.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@RequestBody UserRequest userRequest) {
        User createResult = userService.create(userRequest.toEntity());
        
        if (createResult == null) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Create user failed", null));
        }
        
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Create user success", createResult));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getById(@PathVariable Long id) {
        User getResult = userService.get(id);
        
        if (getResult == null) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "User not found", null));
        }
        
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Found user", getResult));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        List<User> results = userService.getAll(page, pageSize);
        
        if (results.isEmpty()) {
            return ResponseEntity.ok().body(ApiResponse.SKIP_AS_GOOD(HttpStatus.OK.toString(), "No user found", null));
        }
        
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Get users success", results));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> update(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest) {
        
        User updateResult = userService.update(id, userRequest.toEntity());
        
        if (updateResult == null) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Update failed", null));
        }
        
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Update success", updateResult));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> delete(@PathVariable Long id) {
        Boolean deleteResult = userService.delete(id);
        
        if (!deleteResult) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Delete failed", null));
        }
        
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Delete success", null));
    }
}
