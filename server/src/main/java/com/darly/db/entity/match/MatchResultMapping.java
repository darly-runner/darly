package com.darly.db.entity.match;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchResultMapping {
    private String userNickname;
    private String userImage;
    private Short matchResultRank;
    private Integer matchResultTime;
    private Float matchResultPace;

    @QueryProjection
    public MatchResultMapping(String userNickname, String userImage, Short matchResultRank, Integer matchResultTime, Float matchResultPace) {
        this.userNickname = userNickname;
        this.userImage = userImage;
        this.matchResultRank = matchResultRank;
        this.matchResultTime = matchResultTime;
        this.matchResultPace = matchResultPace;
    }
}
