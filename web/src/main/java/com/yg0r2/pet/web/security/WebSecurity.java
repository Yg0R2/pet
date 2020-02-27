package com.yg0r2.pet.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtClaimExtractor jwtClaimExtractor;
    @Autowired
    private JwtTokenFactory jwtTokenFactory;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();

        configSource.registerCorsConfiguration("/api/**", new CorsConfiguration().applyPermitDefaultValues());

        return configSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/*").permitAll()
                    //.antMatchers(HttpMethod.POST, "/sign-in").permitAll()
                    //.anyRequest().authenticated()
            /*.and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenFactory))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtClaimExtractor))
                // Disable Spring Security session creation
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/;
    }

}
