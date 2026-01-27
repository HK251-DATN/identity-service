package edu.hcmut.datn.identity_service.repository;

import edu.hcmut.datn.identity_service.dao.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
