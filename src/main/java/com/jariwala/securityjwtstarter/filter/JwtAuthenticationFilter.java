package com.jariwala.securityjwtstarter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = null;
            String token = httpServletRequest.getHeader("Authorization");
            if (token.startsWith("Bearer ")) {
                jwt = token.replace("Bearer ","");

            }
        } catch (Exception e){
            log.error("Cannot add filter {}" ,e);
        }

    }
}
