package edu.hcmut.datn.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.hcmut.datn.identity_service.dao.GroupPermission;

public interface UserGroupRepository extends JpaRepository<GroupPermission, Object> {

}
