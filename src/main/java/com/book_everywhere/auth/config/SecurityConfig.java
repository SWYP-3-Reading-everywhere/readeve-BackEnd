package com.book_everywhere.auth.config;

import com.book_everywhere.auth.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 필터를 위한 Bean 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 또는 패턴을 사용하여 출처 지정
        config.setAllowedOriginPatterns(Arrays.asList("https://*.bookeverywhere.site","http://localhost:3000"));
        config.setAllowCredentials(true); // 크리덴셜 허용
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        http
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/health")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/env")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/test/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/swagger-ui/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/api/review")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/api/map")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/api/tags")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/api/data/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/api/**")).hasRole("ROLE_MEMBER")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(customOAuth2UserService))
                                .successHandler((request, response, authentication) -> {
                                    // 로그인 성공 후 리다이렉션할 URL 지정
                                    response.sendRedirect("https://www.bookeverywhere.site");
                                }))
        ;
        return http.build();
    }

}