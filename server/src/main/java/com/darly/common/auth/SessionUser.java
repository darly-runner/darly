package com.darly.common.auth;

import com.darly.db.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user){
        this.name = user.getUserNickname();
        this.email = user.getUserEmail();
    }
}
