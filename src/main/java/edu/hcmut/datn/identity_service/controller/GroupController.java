package edu.hcmut.datn.identity_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hcmut.datn.identity_service.dao.Group;
import edu.hcmut.datn.identity_service.dto.request.GroupRequest;
import edu.hcmut.datn.identity_service.dto.response.ApiResponse;
import edu.hcmut.datn.identity_service.service.GroupService;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<ApiResponse<Group>> create(@RequestBody GroupRequest groupRequest) {
        Group createResult = groupService.create(groupRequest.toEntity());

        if (createResult == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Create group failed", null));
        }

        return ResponseEntity.ok()
                .body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Create group success", createResult));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Group>>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        List<Group> results = groupService.getAll(page, pageSize);

        if (results.isEmpty()) {
            return ResponseEntity.ok().body(ApiResponse.SKIP_AS_GOOD(HttpStatus.OK.toString(), "No group found", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Get groups success", results));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Group>> getGroup(@PathVariable Long groupId) {
        Group getResult = groupService.get(groupId);

        if (getResult == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Group not found", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Found group", getResult));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Group>> update(
            @PathVariable Long groupId,
            @RequestBody GroupRequest groupRequest) {

        Group updateResult = groupService.update(groupId, groupRequest.toEntity());

        if (updateResult == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Update failed", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Update success", updateResult));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Group>> delete(@PathVariable Long groupId) {
        Boolean deleteResult = groupService.delete(groupId);

        if (!deleteResult) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.ERROR(HttpStatus.BAD_REQUEST.toString(), "Delete failed", null));
        }

        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.OK.toString(), "Delete success", null));
    }
}
