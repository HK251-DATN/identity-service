package edu.hcmut.datn.identity_service.security.jwt;

import edu.hcmut.datn.identity_service.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtKeyProvider keyProvider;
    
    public String generateToken(User user) {
        return null;
    }
}
