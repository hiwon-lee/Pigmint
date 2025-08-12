package io.github.hiwonlee.pigmint.config; // 본인의 프로젝트 패키지 경로에 맞게 수정하세요.

import io.github.hiwonlee.pigmint.config.oauth.CustomOAuth2UserService; // 이 파일의 경로도 확인하세요.
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security 설정을 활성화합니다.
@RequiredArgsConstructor // final 필드 또는 @NonNull 필드가 포함된 생성자를 자동으로 만들어줍니다.
public class SecurityConfig {

    // 소셜 로그인 성공 후 사용자 정보를 처리할 서비스를 주입받습니다.
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF(Cross-site request forgery) 공격 방지 기능을 비활성화합니다.
                // JWT 같은 토큰 방식을 사용할 때는 세션을 사용하지 않으므로 비활성화해도 안전한 경우가 많습니다.
                .csrf(csrf -> csrf.disable())

                // H2 데이터베이스 콘솔을 사용하기 위해, 특정 헤더 옵션을 비활성화합니다.
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )

                // URL별로 접근 권한을 설정합니다.
                .authorizeHttpRequests(authz -> authz
                        // 아래의 경로들은 로그인 없이 누구나 접근할 수 있도록 허용합니다.
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                        // '/api/v1/**' 경로는 'USER' 역할을 가진 사용자만 접근할 수 있도록 설정합니다.
                        .requestMatchers("/api/v1/**").hasRole("USER")
                        // 위에서 설정한 경로 외의 나머지 모든 경로는 인증된(로그인한) 사용자만 접근할 수 있습니다.
                        .anyRequest().authenticated()
                )

                // 로그아웃 기능을 설정합니다.
                .logout(logout -> logout
                        // 로그아웃 성공 시 사용자를 메인 페이지('/')로 리디렉션합니다.
                        .logoutSuccessUrl("/")
                )

                // OAuth2(소셜) 로그인 기능을 설정합니다.
                .oauth2Login(oauth2 -> oauth2
                        // 로그인이 성공적으로 완료된 후 사용자 정보를 가져올 때의 설정을 담당합니다.
                        .userInfoEndpoint(userInfo -> userInfo
                                // Spring Security의 기본 서비스 대신, 우리가 직접 만든 customOAuth2UserService를 사용하겠다고 설정합니다.
                                // 이 서비스에서 소셜 로그인 사용자의 정보를 처리합니다.
                                .userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }
}