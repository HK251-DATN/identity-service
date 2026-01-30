package edu.hcmut.datn.identity_service.dto.request;

import java.time.LocalDateTime;

import edu.hcmut.datn.identity_service.dao.GroupPermission;

public class GroupPermissionDTO {

    private Long groupId;

    private Long perId;

    private boolean isActive;

    private LocalDateTime validUntil;

    public GroupPermission toEntity() {
        GroupPermission groupPermission = new GroupPermission();

        groupPermission.setGroupId(groupId);
        groupPermission.setPerId(perId);
        groupPermission.setActive(isActive);
        groupPermission.setValidUntil(validUntil);

        return groupPermission;
    }

}
