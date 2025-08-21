package io.github.hiwonlee.pigmint.config; // 본인의 프로젝트 패키지 경로에 맞게 수정하세요.

import io.github.hiwonlee.pigmint.config.jwt.JwtAuthenticationFilter;
import io.github.hiwonlee.pigmint.config.oauth.CustomOAuth2UserService; // 이 파일의 경로도 확인하세요.
import io.github.hiwonlee.pigmint.config.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

// 보안 관련 설정
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final CorsConfigurationSource corsConfigurationSource;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())  // CSRF(Cross-site request forgery) 공격 방지 기능을 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))  // ??이거 공부
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/**").authenticated() // API 경로는 인증 필요
                        .anyRequest().permitAll() // 그 외 경로는 모두 허용
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(customAuthenticationEntryPoint)) // 인증 실패 시 401 반환
                .oauth2Login(oauth2 -> oauth2  // 소셜 로그인 로직
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용


        return http.build();
    }
}