package edu.hcmut.datn.identity_service.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class RsaKeyLoader {
    
    private RsaKeyLoader() {}
    
    public static PrivateKey loadPrivateKey(String pem) {
        try {
            String privateKeyContent = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            
            byte[] decoded = Base64.getDecoder().decode(privateKeyContent);
            
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            
            return keyFactory.generatePrivate(keySpec);
            
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load private key", e);
        }
    }
    
    public static PublicKey loadPublicKey(String pem) {
        try {
            String publicKeyContent = pem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            
            byte[] decoded = Base64.getDecoder().decode(publicKeyContent);
            
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            
            return keyFactory.generatePublic(keySpec);
            
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load public key", e);
        }
    }
}

