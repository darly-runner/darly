package com.darly.db.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMatchMapping {
    private Long userId;
    private String userNickname;
    private Float userPaceAvg;
    private String userImage;
    private Float userTotalDistance;
    private Character userStatus;
    private Integer isHost;

    @Builder
    public UserMatchMapping (Long userId, String userNickname, Float userPaceAvg, String userImage, Float userTotalDistance, Character userStatus, Integer isHost){
        this.userId = userId;
        this.userNickname = userNickname;
        this.userPaceAvg = userPaceAvg;
        this.userImage = userImage;
        this.userTotalDistance = userTotalDistance;
        this.userStatus = userStatus;
        this.isHost = isHost;
    }
}
