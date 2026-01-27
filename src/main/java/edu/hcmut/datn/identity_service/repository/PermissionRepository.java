package edu.hcmut.datn.identity_service.repository;

import edu.hcmut.datn.identity_service.dao.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
