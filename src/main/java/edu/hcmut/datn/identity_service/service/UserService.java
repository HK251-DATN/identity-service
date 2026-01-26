package edu.hcmut.datn.identity_service.service;

import edu.hcmut.datn.identity_service.dao.User;

import java.util.List;

public interface UserService {

    User create(User user);
    
    List<User> getAll (Integer page, Integer pageSize);
    
    User get(Long id);
    
    User update(Long id, User user);
    
    Boolean delete(Long id);
}
