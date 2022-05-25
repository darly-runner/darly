package com.darly.db.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserNowPace implements Comparable<UserNowPace> {
    private Long userId;
    private String userNickname;
    private String userImage;
    private Float nowDistance;
    private Integer nowTime;
    private String nowPace;
    private Integer nowPaceInt;

    @Override
    public int compareTo(UserNowPace o) {
        return Float.compare(this.nowDistance, o.nowDistance) * -1;
    }
}
