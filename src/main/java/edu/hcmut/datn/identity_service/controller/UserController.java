package edu.hcmut.datn.identity_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dto.misc.GroupBasicView;
import edu.hcmut.datn.identity_service.dto.misc.PermissionBasicView;
import edu.hcmut.datn.identity_service.dto.request.UserRequest;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import edu.hcmut.datn.identity_service.security.jwt.JwtTokenGenerator;
import edu.hcmut.datn.identity_service.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<User>> create(@RequestBody UserRequest userRequest) {
        User createResult = userService.create(userRequest.toEntity());

        if (createResult == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Create user failed", null));
        }

        return ResponseEntity.ok()
                .body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Create user success", createResult));
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserRequest userRequest) {
        Boolean loginResult = userService.authenticate(userRequest.getEmail(), userRequest.getPassword());

        if (!loginResult) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Invalid credential", null));
        }

        User curUser = userService.getByEmail(userRequest.getEmail());

        String token = jwtTokenGenerator.generateToken(curUser);

        Map<String, String> result = Map.of("accessToken", token);

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Valid credential", result));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_VIEW') or #id == principal.id")
    public ResponseEntity<ApiResponse<User>> getById(@PathVariable Long id) {
        User getResult = userService.get(id);

        if (getResult == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "User not found", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Found user", getResult));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public ResponseEntity<ApiResponse<List<User>>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        List<User> results = userService.getAll(page, pageSize);

        if (results.isEmpty()) {
            return ResponseEntity.ok().body(ApiResponse.SKIP_AS_GOOD(HttpStatus.OK.toString(), "No user found", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Get users success", results));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE') or #id == principal.id")
    public ResponseEntity<ApiResponse<User>> update(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest) {

        User updateResult = userService.update(id, userRequest.toEntity());

        if (updateResult == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Update failed", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Update success", updateResult));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE') or #id == principal.id")
    public ResponseEntity<ApiResponse<User>> delete(@PathVariable Long id) {
        Boolean deleteResult = userService.delete(id);

        if (!deleteResult) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Delete failed", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Delete success", null));
    }

    @GetMapping("/{userId}/group")
    @PreAuthorize("hasAuthority('GROUP_MANAGE')")
    public ResponseEntity<ApiResponse<List<GroupBasicView>>> getGroups(@PathVariable Long userId) {
        List<GroupBasicView> results = userService.getUserGroups(userId);

        if (results.isEmpty()) {
            return ResponseEntity.ok()
                    .body(ApiResponse.SKIP_AS_GOOD(HttpStatus.OK.toString(), "No group found", null));
        }

        return ResponseEntity.ok()
                .body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Get user's groups success", results));
    }

    @GetMapping("/{userId}/permission")
    @PreAuthorize("hasAuthority('USER_VIEW') and hasAuthority('PERMISSION_VIEW')")
    public ResponseEntity<ApiResponse<List<PermissionBasicView>>> getPermissions(@PathVariable Long userId) {
        List<PermissionBasicView> results = userService.getUserPermissions(userId);

        if (results.isEmpty()) {
            return ResponseEntity.ok()
                    .body(ApiResponse.SKIP_AS_GOOD(HttpStatus.OK.toString(), "No permission found", null));
        }

        return ResponseEntity.ok()
                .body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Get user's permissions success", results));
    }
}
