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
    // SecurityContextHolder에서 현재 로그인한 사용자의 인증 정보를 가져옴

    // 아래 라인은 절대로 전역으로 두면 안된다.
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Transactional(readOnly = true)
    public UserInfoResponse findMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // getCurrentUserId()처럼 null 체크를 추가하여 안정성을 높입니다.
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        // 2. 인증 정보에서 사용자의 이름(이메일)을 가져온다.
        final String userEmail = authentication.getName();

        // 3. 이메일을 사용해 데이터베이스에서 사용자 정보를 조회한다.
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다." + userEmail));

        return new UserInfoResponse(user.getName(), user.getEmail(), user.getRole());
    }

    @Transactional(readOnly = true)
    public Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나, 사용자 이름이 없는 경우 예외 발생
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        // 인정정보에서 사용자 이메일 가져옴
        String userEmail = authentication.getName();

        // 이메일을 사용해 DB에서 사용자를 찾는다.
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다:" + userEmail));

        // ID 반환
        return user.getId();
    }
}
