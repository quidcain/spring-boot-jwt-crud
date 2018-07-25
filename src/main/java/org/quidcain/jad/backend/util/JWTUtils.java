package org.quidcain.jad.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    private long expiration;
    private String secret;

    public JWTUtils(@Value("${jwt.expiration}") long expiration,
                    @Value("${jwt.secret}") String secret) {
        this.expiration = expiration;
        this.secret = secret;
    }

    public String extractUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
