package edu.hcmut.datn.identity_service.service;

import java.util.List;

import edu.hcmut.datn.identity_service.dao.Group;

public interface GroupService {

    Group create(Group group);

    Group get(Long id);

    List<Group> getAll(Integer page, Integer pageSize);

    Group update(Long id, Group group);

    boolean delete(Long id);
}
