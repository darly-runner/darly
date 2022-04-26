package com.darly.api.response.account;

import com.darly.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoUserRes {
    private String email;
    private String name;

    public User toEntity(){
        return User.builder()
                .userNickname(name)
                .userEmail(email)
                .build();
    }
}
