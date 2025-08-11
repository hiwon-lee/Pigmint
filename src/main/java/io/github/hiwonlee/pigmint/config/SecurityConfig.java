package io.github.hiwonlee.pigmint.config; // 본인 패키지에 맞게 수정
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        // '/**'는 모든 경로를 의미합니다. 모든 경로에 대한 요청을 허용합니다.
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}