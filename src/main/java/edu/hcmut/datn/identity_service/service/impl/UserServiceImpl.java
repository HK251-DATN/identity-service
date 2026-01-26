package edu.hcmut.datn.identity_service.service.impl;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.repository.UserRepository;
import edu.hcmut.datn.identity_service.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public User create (User user) {
        // TODO: This is not safe, add hash function for password
        try {
            Optional<User> curUser = userRepository.findByUserEmail(user.getUserEmail());
            if (curUser.isPresent()) {
                throw new RuntimeException("Duplicated user");
            } else {
                return userRepository.save(user);
            }
        } catch (Exception e) {
            // TODO: Log the exception
            return null;
        }
    }
    
    @Override
    public List<User> getAll (Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }
    
    @Override
    public User get (Long id) {
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
    public User update (Long id, User user) {
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
    public Boolean delete (Long id) {
        try {
            userRepository.findById(id).ifPresent(user -> userRepository.delete(user));
            
            return true;
        } catch (Exception e) {
            // TODO: Log the exception
            return false;
        }
    }
}
