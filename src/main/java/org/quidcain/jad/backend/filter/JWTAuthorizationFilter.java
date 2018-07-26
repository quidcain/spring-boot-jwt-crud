package org.quidcain.jad.backend.filter;

import org.quidcain.jad.backend.util.JWTUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

import static org.quidcain.jad.backend.constants.ApiConstants.AUTHORIZATION_HEADER;
import static org.quidcain.jad.backend.constants.ApiConstants.TOKEN_PREFIX;

@Component
public class JWTAuthorizationFilter extends GenericFilterBean {
    private JWTUtils jwtUtils;

    public JWTAuthorizationFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication((HttpServletRequest) request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String tokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (tokenHeader != null) {
            String token = tokenHeader.replace(TOKEN_PREFIX, "");
            String username = jwtUtils.extractUsernameFromToken(token);
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            }
            return null;
        }
        return null;
    }
}
