package io.github.hiwonlee.pigmint.config.oauth;

import io.github.hiwonlee.pigmint.config.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // JWT 토큰을 생성하는 유틸리티 클래스 (별도 구현 필요)
     private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 여기서 oAuth2User 정보를 바탕으로 JWT를 생성.
        // String token = jwtTokenProvider.generateToken(oAuth2User);
        String token = jwtTokenProvider.generateToken(authentication); // <-- 실제로는 JWT 생성 로직 필요 (완료)

        // 프론트엔드로 리다이렉트할 URL을 만듭니다.
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth/callback") // 프론트엔드 주소
                .queryParam("token", token)
                .build().toUriString();
//        System.out.println("타깃 url : " + targetUrl);

        // 만든 URL로 리다이렉트.
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}