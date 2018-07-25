package org.quidcain.jad.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.quidcain.jad.backend.config.JWTConfig;
import org.quidcain.jad.backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTConfig jwtConfig;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(successfulAuthentication(auth));
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<String> unsuccessfulAuthentication(AuthenticationException e) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    protected Map<String, String> successfulAuthentication(Authentication auth) {
        String token = generateToken(((UserDetails) auth.getPrincipal()).getUsername());
        return generatePayload(token);
    }

    protected String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

    protected Map<String, String> generatePayload(String token) {
        Map<String,String> payload = new HashMap<>();
        payload.put(jwtConfig.getTokenPayloadProperty(), jwtConfig.getTokenPrefix() + token);
        return payload;
    }
}
