package org.quidcain.jad.backend.constants;

public final class ApiConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PAYLOAD_PROPERTY = "token";
    public static final String MESSAGE_PAYLOAD_PROPERTY = "message";
    public static final String STATUS_PAYLOAD_PROPERTY = "status";
    public static final String CURRENT_PAGE_PAYLOAD_PROPERTY = "currentPage";
    public static final String TOTAL_PAGES_PAYLOAD_PROPERTY = "totalPages";
    public static final String CONTENT_PAYLOAD_PROPERTY = "content";

    private ApiConstants() {
        throw new AssertionError();
    }
}
