package edu.hcmut.datn.identity_service.service;

import edu.hcmut.datn.identity_service.dao.Group;

import java.util.List;

public interface GroupService {
    
    Group create();
    
    Group get(Long id);
    
    List<Group> getAll();
    
    Group update(Long id, Group group);
    
    boolean delete(Long id);
}
