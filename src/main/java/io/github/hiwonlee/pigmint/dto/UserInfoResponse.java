package io.github.hiwonlee.pigmint.dto;

import io.github.hiwonlee.pigmint.domain.Role;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private String nickname;
    private String email;
    private Role role;

    public UserInfoResponse(String nickname, String email, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}