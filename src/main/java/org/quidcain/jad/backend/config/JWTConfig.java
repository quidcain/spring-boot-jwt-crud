package org.quidcain.jad.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PAYLOAD_PROPERTY = "token";

    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.secret}")
    private String secret;
    private String tokenPrefix = TOKEN_PREFIX;
    private String headerString = HEADER_STRING;
    private String tokenPayloadProperty = TOKEN_PAYLOAD_PROPERTY;

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getHeaderString() {
        return headerString;
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
    }

    public String getTokenPayloadProperty() {
        return tokenPayloadProperty;
    }

    public void setTokenPayloadProperty(String tokenPayloadProperty) {
        this.tokenPayloadProperty = tokenPayloadProperty;
    }
}
