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

import static org.quidcain.jad.backend.constants.AuthConstants.TOKEN_PAYLOAD_PROPERTY;
import static org.quidcain.jad.backend.constants.AuthConstants.TOKEN_PREFIX;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
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
        String token = jwtUtils.generateToken(((UserDetails) auth.getPrincipal()).getUsername());
        return generatePayload(token);
    }

    protected Map<String, String> generatePayload(String token) {
        Map<String,String> payload = new HashMap<>();
        payload.put(TOKEN_PAYLOAD_PROPERTY, TOKEN_PREFIX + token);
        return payload;
    }
}
