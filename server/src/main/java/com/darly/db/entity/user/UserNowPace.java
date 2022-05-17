package com.darly.db.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserNowPace implements Comparable<UserNowPace> {
    private Long userId;
    private String userNickname;
    private String userImage;
    private Float nowDistance;
    private Integer nowTime;
    private Integer nowPace;

    @Override
    public int compareTo(UserNowPace o) {
        return Float.compare(this.nowDistance, o.nowDistance) * -1;
    }
}
