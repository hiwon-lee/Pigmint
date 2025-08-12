package io.github.hiwonlee.pigmint.repository; // 본인의 프로젝트 패키지 경로에 맞게 수정하세요.

import io.github.hiwonlee.pigmint.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드입니다.
    Optional<User> findByEmail(String email);
}