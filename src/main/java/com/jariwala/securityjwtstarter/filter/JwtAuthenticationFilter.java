package com.jariwala.securityjwtstarter.filter;

import com.jariwala.securityjwtstarter.config.JwtTokenProvider;
import com.jariwala.securityjwtstarter.model.User;
import com.jariwala.securityjwtstarter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = null;
            String token = httpServletRequest.getHeader("Authorization");
            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                jwt = token.substring(7);
            }
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long id = jwtTokenProvider.getUserIdFromJWT(jwt);

                User user = userService.loadUserByUserId(id);
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot add filter {}", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
