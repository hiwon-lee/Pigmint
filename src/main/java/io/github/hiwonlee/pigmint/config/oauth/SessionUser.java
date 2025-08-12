package io.github.hiwonlee.pigmint.config.oauth; // 본인의 프로젝트 패키지 경로에 맞게 수정하세요.

import io.github.hiwonlee.pigmint.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    // 직렬화 기능을 사용하겠다고 명시하는 버전 ID입니다.
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String picture;

    // User 엔티티를 받아 필요한 정보만 필터링하여 저장합니다.
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}