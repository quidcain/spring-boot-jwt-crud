package org.quidcain.jad.backend.constants;

public final class AuthConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PAYLOAD_PROPERTY = "token";

    private AuthConstants() {
        throw new AssertionError();
    }
}
