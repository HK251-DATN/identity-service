package edu.hcmut.datn.identity_service.service.impl;

import edu.hcmut.datn.identity_service.dao.Group;
import edu.hcmut.datn.identity_service.repository.GroupRepository;
import edu.hcmut.datn.identity_service.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GroupServiceImpl implements GroupService {
    
    @Autowired
    private GroupRepository groupRepository;
    
    @Override
    public Group create () {
        return null;
    }
    
    @Override
    public Group get (Long id) {
        return null;
    }
    
    @Override
    public List<Group> getAll () {
        return List.of();
    }
    
    @Override
    public Group update (Long id, Group group) {
        return null;
    }
    
    @Override
    public boolean delete (Long id) {
        return false;
    }
}
