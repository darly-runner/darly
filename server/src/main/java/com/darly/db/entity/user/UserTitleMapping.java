package com.darly.db.entity.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTitleMapping {
    private Long userId;
    private String userNickname;
    private String userMessage;
    private String userImage;

    @QueryProjection
    public UserTitleMapping(Long userId, String userNickname, String userMessage, String userImage) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userMessage = userMessage;
        this.userImage = userImage;
    }
}
