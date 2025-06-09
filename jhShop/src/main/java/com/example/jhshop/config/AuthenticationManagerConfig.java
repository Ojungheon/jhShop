package com.example.jhshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig extends AbstractHttpConfigurer<AuthenticationManagerConfig, HttpSecurity> {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {

    }
}

