package com.darly.api.response.crew;

import com.darly.db.entity.crew.CrewSummaryMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RankEntity implements Comparable<RankEntity>{
    private String userNickname;
    private String userImage;
    private Float userDistance;
    private Float userPace;

    @Builder
    public RankEntity(CrewSummaryMapping crewSummaryMapping) {
        this.userNickname = crewSummaryMapping.getUserNickname();
        this.userImage = crewSummaryMapping.getUserImage();
        this.userDistance = crewSummaryMapping.getDayDistance();
        this.userPace = crewSummaryMapping.getDayPace();
    }

    @Override
    public int compareTo(RankEntity o) {
        return this.userDistance.compareTo(o.userDistance);
    }
}
