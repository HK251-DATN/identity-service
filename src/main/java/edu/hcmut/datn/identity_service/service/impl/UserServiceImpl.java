package edu.hcmut.datn.identity_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dto.misc.GroupBasicView;
import edu.hcmut.datn.identity_service.dto.misc.PermissionBasicView;
import edu.hcmut.datn.identity_service.repository.UserRepository;
import edu.hcmut.datn.identity_service.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(User user) {
        try {
            Optional<User> curUser = userRepository.findByUserEmail(user.getUserEmail());
            if (curUser.isPresent()) {
                throw new RuntimeException("Duplicated user");
            } else {
                user.setHashedPwd(passwordEncoder.encode(user.getHashedPwd()));
                return userRepository.save(user);
            }
        } catch (Exception e) {
            // TODO: Log the exception
            return null;
        }
    }

    @Override
    public List<User> getAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }

    @Override
    public User get(Long id) {
        try {
            Optional<User> curUser = userRepository.findById(id);
            if (curUser.isPresent()) {
                return curUser.get();
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            // TODO: Log the exception
            return null;
        }
    }

    @Override
    public User update(Long id, User user) {
        try {
            Optional<User> curUser = userRepository.findById(id);
            if (curUser.isPresent()) {
                curUser.get().setUserEmail(user.getUserEmail());
                curUser.get().setHashedPwd(user.getHashedPwd());
                return userRepository.save(curUser.get());
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            // TODO: Log the exception
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            userRepository.findById(id).ifPresent(user -> userRepository.delete(user));

            return true;
        } catch (Exception e) {
            // TODO: Log the exception
            return false;
        }
    }

    @Override
    public Boolean authenticate(String email, String rawPassword) {
        if (!userRepository.existsByUserEmail(email)) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, userRepository.findByUserEmail(email).get().getHashedPwd());
    }

    @Override
    public List<PermissionBasicView> getUserPermissions(Long userId) {
        return userRepository.getUserPermissions(userId);
    }

    @Override
    public List<GroupBasicView> getUserGroups(Long userId) {
        return userRepository.getUserGroups(userId);
    }

}
