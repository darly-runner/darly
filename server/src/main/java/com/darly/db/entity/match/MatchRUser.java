package com.darly.db.entity.match;

import com.darly.db.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchRUser implements Comparable<MatchRUser>{
    private Long userId;
    private String userNickname;
    private Integer userPaceAvg;
    private String userImage;


    @Override
    public int compareTo(MatchRUser o) {
        return Integer.compare(
                this.userPaceAvg, o.userPaceAvg
        );
    }

    @Builder
    public MatchRUser(User user, Long totalRecordNum) {
        this.userId = user.getUserId();
        this.userNickname = user.getUserNickname();
        this.userImage = user.getUserImage();

        Float totalPace = user.getUserTotalPace();
        this.userPaceAvg = Math.round((totalPace / totalRecordNum)*100);
    }
}
