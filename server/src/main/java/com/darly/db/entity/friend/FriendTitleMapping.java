package com.darly.db.entity.friend;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class FriendTitleMapping {
    private Long userId;
    private String userNickname;
    private String userMessage;
    private String userImage;

    @QueryProjection
    public FriendTitleMapping(Long userId, String userNickname, String userMessage, String userImage){
        this.userId = userId;
        this.userNickname = userNickname;
        this.userMessage = userMessage;
        this.userImage = userImage;
    }
}
