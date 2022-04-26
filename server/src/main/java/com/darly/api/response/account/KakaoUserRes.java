package com.darly.api.response.account;

import com.darly.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class KakaoUserRes {
    private Long id;
    private Map<String, Object> kakao_account;

    public User toEntity() {
        String email = (boolean)kakao_account.get("has_email") ? (String)kakao_account.get("email") : id.toString();
        return User.builder()
                .userNickname(email)
                .userEmail(email)
                .build();
    }
}
