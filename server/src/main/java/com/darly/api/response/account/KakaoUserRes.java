package com.darly.api.response.account;

import com.darly.db.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class KakaoUserRes {
    private Long id;
    private Map<String, Object> kakao_account;

    public User toEntity() {
        String email = (boolean) kakao_account.get("has_email") ? (String) kakao_account.get("email") : id.toString();
        return User.builder()
                .userNickname(email)
                .userEmail(email)
                .build();
    }
}
