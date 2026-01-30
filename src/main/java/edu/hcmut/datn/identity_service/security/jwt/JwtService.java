package edu.hcmut.datn.identity_service.security.jwt;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hcmut.datn.identity_service.dao.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    @Autowired
    private JwtKeyProvider keyProvider;

    public String generateToken(User user) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .setIssuer("identity-service")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600 * 24)))
                .claim("userId", user.getUserId())
                .claim("userEmail", user.getUserEmail())
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
