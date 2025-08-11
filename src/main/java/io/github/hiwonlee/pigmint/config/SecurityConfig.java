package io.github.hiwonlee.pigmint.config; // 본인 패키지에 맞게 수정

import io.github.hiwonlee.pigmint.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // final 필드가 포함된 생성자를 자동으로 만들어줍니다. (의존성 주입)
public class SecurityConfig {

    // ▼▼▼ 1. 우리가 만든 CustomOAuth2UserService를 주입받습니다. ▼▼▼
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (개발 편의를 위해)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // H2 콘솔 사용을 위해
                .authorizeHttpRequests(authz -> authz
                        // 아래 주소들은 인증 없이 누구나 접근 가능하도록 허용
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        // 나머지 모든 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 메인 페이지로 이동
                )
                // ▼▼▼ 2. OAuth2 로그인 설정을 시작합니다. ▼▼▼
                .oauth2Login(oauth2 -> oauth2
                        // ▼▼▼ 3. 사용자 정보를 가져온 후의 처리를 담당할 서비스를 지정합니다. ▼▼▼
                        .userInfoEndpoint(userInfo -> userInfo
                                // Spring Security의 기본 서비스 대신, 우리가 만든 customOAuth2UserService를 사용하겠다고 설정합니다.
                                .userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }
}