package com.franchise.support.auth.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import com.franchise.support.auth.converter.KeycloakRoleConverter;
import com.franchise.support.auth.filter.CsrfCookieFilter;
import com.franchise.support.auth.handler.CustomAccessDeniedHandler;
import com.franchise.support.auth.handler.CustomBasicAuthenticationEntryPoint;

@Profile("prod")
@EnableWebSecurity
@Configuration
public class SecurityProdConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(request -> getCorsConfiguration()))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/api/v1/health")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .requiresChannel(registry -> registry.anyRequest().requiresSecure()) // Only HTTPS
                .exceptionHandling(exConfig -> exConfig.accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/health", "/error").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated());

        http.oauth2ResourceServer(resourceServerConfig -> resourceServerConfig
                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(getJwtAuthenticationConverter())));

        return http.build();
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("https://localhost:3000")); // TODO: 운영환경 클라이언트 Origin 등록
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(3600L);
        return config;
    }

    private JwtAuthenticationConverter getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");
        return jwtAuthenticationConverter;
    }

}