package edu.hcmut.datn.identity_service.security.jwt;

import edu.hcmut.datn.identity_service.util.RsaKeyLoader;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class JwtKeyProvider {
    
    @Getter
    private final PrivateKey privateKey = loadPrivateKey();
    
    @Getter
    private final PublicKey publicKey = loadPublicKey();
    
    private PrivateKey loadPrivateKey() {
        Resource resource = new ClassPathResource("keys/private.pem");
        try {
            String pem = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            return RsaKeyLoader.loadPrivateKey(pem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private PublicKey loadPublicKey() {
        Resource resource = new ClassPathResource("keys/public.pem");
        try {
            String pem = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            return RsaKeyLoader.loadPublicKey(pem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
