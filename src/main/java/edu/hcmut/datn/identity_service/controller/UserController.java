package edu.hcmut.datn.identity_service.controller;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dto.request.UserRequest;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import edu.hcmut.datn.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ApiResponse<User> create(@RequestBody UserRequest userRequest) {
        User createResult = userService.create(userRequest.toEntity());
        
        if (createResult == null) {
            return ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Create user failed", null);
        }
        
        return ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Create user success", createResult);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<User> getById(@PathVariable Long id) {
        User getResult = userService.get(id);
        
        if (getResult == null) {
            return ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "User not found", null);
        }
        
        return ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Found user", getResult);
    }
    
    @GetMapping
    public ApiResponse<List<User>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        List<User> results = userService.getAll(page, pageSize);
        
        if (results.isEmpty()) {
            return ApiResponse.SKIP_AS_GOOD(HttpStatus.OK.toString(), "No user found", null);
        }
        
        return ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Get users success", results);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<User> update(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest) {
        
        User updateResult = userService.update(id, userRequest.toEntity());
        
        if (updateResult == null) {
            return ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Update failed", null);
        }
        
        return ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Update success", updateResult);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<User> delete(@PathVariable Long id) {
        Boolean deleteResult = userService.delete(id);
        
        if (!deleteResult) {
            return ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Delete failed", null);
        }
        
        return ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Delete success", null);
    }
}
