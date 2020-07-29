package com.yg0r2.auth.web.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtClaimExtractor jwtClaimExtractor;
    @Autowired
    private JwtTokenExtractor jwtTokenExtractor;
    @Autowired
    private JwtTokenValidator jwtTokenValidator;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = jwtTokenExtractor.extract(request);

            if (jwtTokenValidator.isValid(jwtToken)) {
                doFilter(jwtToken, request);
            }
        }
        catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        }

        filterChain.doFilter(request, response);
    }

    private void doFilter(String jwtToken, HttpServletRequest request) {
        String userName = jwtClaimExtractor.extractSubject(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
    }

}
