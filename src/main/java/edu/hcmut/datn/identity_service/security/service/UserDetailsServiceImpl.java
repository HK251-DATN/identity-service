package edu.hcmut.datn.identity_service.security.service;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        return null;
    }
}
