package com.darly.db.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMatchMapping {
    private String userNickname;
    private Float userPaceAvg;
    private Character userStatus;
    private Integer isHost;

    @Builder
    public UserMatchMapping (String userNickname, Float userPaceAvg, Character userStatus, Integer isHost){
        this.userNickname = userNickname;
        this.userPaceAvg = userPaceAvg;
        this.userStatus = userStatus;
        this.isHost = isHost;
    }
}
