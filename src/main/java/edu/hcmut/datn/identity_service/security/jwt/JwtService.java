package edu.hcmut.datn.identity_service.security.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hcmut.datn.identity_service.dao.User;
import edu.hcmut.datn.identity_service.dto.misc.PermissionBasicView;
import edu.hcmut.datn.identity_service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    @Autowired
    private JwtKeyProvider keyProvider;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(User user) {
        Instant now = Instant.now();

        List<String> permissions = userRepository.getUserPermissions(user.getUserId()).stream()
                .map(PermissionBasicView::getPerCode).toList();

        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .setIssuer("identity-service")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600 * 24)))
                .claim("userId", user.getUserId())
                .claim("userEmail", user.getUserEmail())
                .claim("permissions", permissions)
                .signWith(keyProvider.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
        // return null;
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyProvider.getPublicKey())
                .requireIssuer("identity-service")
                .build()
                .parseClaimsJws(token);
    }
}
