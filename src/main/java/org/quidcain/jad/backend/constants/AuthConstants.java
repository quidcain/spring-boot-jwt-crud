package org.quidcain.jad.backend.constants;

public final class AuthConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PAYLOAD_PROPERTY = "token";
    public static final String ERROR_PAYLOAD_PROPERTY = "message";
    public static final String STATUS_PAYLOAD_PROPERTY = "status";

    private AuthConstants() {
        throw new AssertionError();
    }
}
