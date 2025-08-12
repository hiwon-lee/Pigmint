package io.github.hiwonlee.pigmint.domain; // 본인의 프로젝트 패키지 경로에 맞게 수정하세요.

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // Spring Security에서는 권한 코드 앞에 항상 "ROLE_"이 붙어야 합니다.
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}