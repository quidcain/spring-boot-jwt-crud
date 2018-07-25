package org.quidcain.jad.backend.controller;

import org.quidcain.jad.backend.model.User;
import org.quidcain.jad.backend.util.JWTUtils;
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

import java.util.HashMap;
import java.util.Map;

import static org.quidcain.jad.backend.constants.AuthConstants.*;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/users/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(successfulAuthentication(auth));
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Map<String, ?>> unsuccessfulAuthentication(AuthenticationException e) {
        SecurityContextHolder.clearContext();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(generateUnsuccessfulPayload(status, e.getMessage()));
    }

    protected Map<String, ?> generateUnsuccessfulPayload(HttpStatus status, String message) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(STATUS_PAYLOAD_PROPERTY, status.value());
        payload.put(ERROR_PAYLOAD_PROPERTY, message);
        return payload;
    }

    protected Map<String, String> successfulAuthentication(Authentication auth) {
        String token = jwtUtils.generateToken(((UserDetails) auth.getPrincipal()).getUsername());
        return generateSuccessfulPayload(token);
    }

    protected Map<String, String> generateSuccessfulPayload(String token) {
        Map<String,String> payload = new HashMap<>();
        payload.put(TOKEN_PAYLOAD_PROPERTY, TOKEN_PREFIX + token);
        return payload;
    }
}
