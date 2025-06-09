package com.example.jhshop.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerConfig authenticationManagerConfig;

    // JWT토큰을 인증을 한다. 그래서 인증에서 HttpSession을 사용하지 않는다.
    // JWT의 장점을 찾아보자.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityContext((context) -> context.requireExplicitSave(false)) // 선택적
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // cors 기본 적용
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/signup", "/login", "/users/refresh").permitAll()
                        .requestMatchers("/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers("/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                        .anyRequest().hasAnyRole("USER", "MANAGER", "ADMIN")
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
                .apply(authenticationManagerConfig);


    }