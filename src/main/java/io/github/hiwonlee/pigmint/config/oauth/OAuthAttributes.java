package io.github.hiwonlee.pigmint.config.oauth;


import io.github.hiwonlee.pigmint.domain.Role;
import io.github.hiwonlee.pigmint.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // 소셜 로그인에서 받아온 사용자 정보 원본
    private String nameAttributeKey; // 사용자 이름의 기준이 되는 Key (예: 'id', 'sub')
    private String name;
    private String email;
    private String picture;
    private String provider;
    private String providerId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String provider, String providerId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.providerId = providerId;

    }

    // 소셜 플랫폼 이름(registrationId)에 따라 적절한 of... 메소드를 호출하여 OAuthAttributes 객체를 생성합니다.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        // 추후 구글 로그인을 추가할 경우를 대비
        // if ("google".equals(registrationId)) {
        //     return ofGoogle(userNameAttributeName, attributes);
        // }

        // 여기에 네이버 로그인 등을 추가할 수 있습니다.
        return null; // 지원하지 않는 소셜 로그인이면 null 반환
    }

    // 카카오에서 받아온 JSON 데이터를 파싱하여 OAuthAttributes 객체를 만듭니다.
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        // 카카오 응답에서 'kakao_account'는 JSON 객체이므로 Map으로 변환
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // 'kakao_account' 안에 있는 'profile' 또한 JSON 객체이므로 Map으로 변환
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .provider("kakao")
                .providerId(String.valueOf(attributes.get("id"))) // 카카오 응답의 "id" 필드가 고유 ID
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // 이 OAuthAttributes 객체를 기반으로 User 엔티티를 생성합니다.
    // 처음 가입하는 사용자일 경우, 이 메소드를 통해 User 엔티티가 생성됩니다.
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .provider(provider)
                .providerId(providerId)
                .role(Role.USER) // 가입 시 기본 권한은 USER로 설정
                .build();
    }
}