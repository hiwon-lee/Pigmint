package io.github.hiwonlee.pigmint.controller;

import io.github.hiwonlee.pigmint.dto.UserInfoResponse;
import io.github.hiwonlee.pigmint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/me")
    public ResponseEntity<UserInfoResponse> getMyInfo() {
        // 현재 로그인한 사용자 정보를 DB에서 조회해
        // UserInfoResponse DTO에 담아 반환한다.
         UserInfoResponse userInfo = userService.findMyInfo();
         return ResponseEntity.ok(userInfo);
//        System.out.println("들어오긴 했음");
        // 임시 응답
//        return ResponseEntity.ok(new UserInfoResponse("Pigmint_User", "user@example.com"));
    }
}
