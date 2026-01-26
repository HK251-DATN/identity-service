package edu.hcmut.datn.identity_service.repository;

import edu.hcmut.datn.identity_service.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String email);

}
