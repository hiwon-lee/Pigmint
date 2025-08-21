package io.github.hiwonlee.pigmint.domain; // 본인의 프로젝트 패키지 경로에 맞게 수정하세요.

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동으로 만들어줍니다.
@Entity // 이 클래스가 데이터베이스의 테이블과 매핑되는 엔티티 클래스임을 나타냅니다.
@Table(name = "users") // DB 예약어와 충돌을 피하기 위해 테이블 이름을 "users"로 지정
public class User {

    @Id // 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다 (자동 증가).
    private Long id;

    @Column(nullable = false) // 해당 컬럼이 null 값을 허용하지 않도록 설정합니다.
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String provider; // 소셜 로그인 제공자 (예: "kakao")

    @Column
    private String providerId; // 소셜 로그인 제공자에서의 고유 ID


    @Column
    private String picture;

    @Enumerated(EnumType.STRING) // Enum 값을 DB에 저장할 때, Enum의 이름을 문자열로 저장하도록 설정합니다.
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.picture = picture;
        this.role = role;
    }

    // 소셜 로그인 시, 이미 가입된 사용자의 정보가 변경되었을 때 업데이트하는 메소드입니다.
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    // 사용자의 권한(Role)을 반환하는 메소드입니다.
    public String getRoleKey() {
        return this.role.getKey();
    }
}