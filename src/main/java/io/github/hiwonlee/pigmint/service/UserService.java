package io.github.hiwonlee.pigmint.service;


import io.github.hiwonlee.pigmint.domain.User;
import io.github.hiwonlee.pigmint.dto.UserInfoResponse;
import io.github.hiwonlee.pigmint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserInfoResponse findMyInfo() {
        // 1. SecurityContextHolder에서 현재 로그인한 사용자의 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. 인증 정보에서 사용자의 이름(이메일)을 가져온다.
        String userEmail = authentication.getName();

        // 3. 이메일을 사용해 데이터베이스에서 사용자 정보를 조회한다.
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다." + userEmail));

        return new UserInfoResponse(user.getName(), user.getEmail(), user.getRole());
    }
}
