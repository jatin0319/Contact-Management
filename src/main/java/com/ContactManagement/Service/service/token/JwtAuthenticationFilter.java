package com.ContactManagement.Service.service.token;

import com.ContactManagement.Service.helper.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(CustomUserDetailsServiceImpl customUserDetailsService, JwtUtil jwtUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer"))
            throw new RuntimeException("Token not present");

        String token = authorization.split("Bearer ")[1];
        String username = null;
        try {
            username = jwtUtil.extractUsername(token);
        }catch (IllegalArgumentException illegalArgumentException){
            throw new RuntimeException("Invalid Token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new RuntimeException("Token has been expired");
        }catch (MalformedJwtException malformedJwtException){
            throw new RuntimeException("Malformed token");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if(!jwtUtil.validateToken(token, userDetails)){
                throw new RuntimeException("Invalid Token");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return path.contains("swagger-ui") || path.contains("v2") ||
                path.contains("token") || path.contains("swagger-resources");
    }
}
