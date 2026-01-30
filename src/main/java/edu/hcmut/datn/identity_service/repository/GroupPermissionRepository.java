package edu.hcmut.datn.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hcmut.datn.identity_service.dao.GroupPermission;
import edu.hcmut.datn.identity_service.dao.UserGroup;

public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {

}
