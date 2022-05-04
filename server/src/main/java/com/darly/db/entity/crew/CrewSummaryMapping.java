package com.darly.db.entity.crew;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrewSummaryMapping {
    private String userNickname;
    private String userImage;
    private Float dayDistance;
    private Long datTime;
    private Integer dayNum;
    private Integer dayPace;

    @QueryProjection
    public CrewSummaryMapping(String userNickname, String userImage, Float dayDistance, Long datTime, Integer dayNum, Integer dayPace) {
        this.userNickname = userNickname;
        this.userImage = userImage;
        this.dayDistance = dayDistance;
        this.datTime = datTime;
        this.dayNum = dayNum;
        this.dayPace = dayPace;
    }
}
