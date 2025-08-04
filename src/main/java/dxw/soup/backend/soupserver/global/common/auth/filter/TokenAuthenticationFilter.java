package dxw.soup.backend.soupserver.global.common.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dxw.soup.backend.soupserver.global.common.auth.service.TokenProvider;
import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import dxw.soup.backend.soupserver.global.common.code.GlobalErrorCode;
import dxw.soup.backend.soupserver.global.common.dto.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String BEARER_AUTH = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String accessToken = getAccessToken(authorizationHeader);

        if (response.isCommitted()) {
            return;
        }

        if (!tokenProvider.validToken(accessToken)) {
            sendUnauthorizedResponse(response);
            return;
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_AUTH)) {
            return authorizationHeader.substring(BEARER_AUTH.length());
        }

        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {

        ErrorCode errorCode = GlobalErrorCode.INVALID_ACCESS_TOKEN;

        response.addHeader("Content-Type", "application/json; charset=UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(errorCode)));
        response.getWriter().flush();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludes = {"/auth/", "/swagger-ui", "/api-docs"};
        String path = request.getRequestURI();

        return Arrays.stream(excludes).anyMatch(path::startsWith);
    }
}
