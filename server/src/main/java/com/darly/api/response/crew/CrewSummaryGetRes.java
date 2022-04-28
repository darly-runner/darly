package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.CrewSummaryMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("CrewSummaryGetResponse")
public class CrewSummaryGetRes extends BaseResponseBody {
    private Integer crewPeopleNum;
    private Float crewDistance;
    private Long crewTime;
    private List<RankEntity> ranks;

    @Builder
    public CrewSummaryGetRes(Integer statusCode, String message, List<CrewSummaryMapping> summaryList) {
        super(statusCode, message);
        crewDistance = 0F;
        crewTime = 0L;
        ranks = new ArrayList<>();
        this.crewPeopleNum = summaryList.size();
        for (CrewSummaryMapping summary : summaryList) {
            crewDistance += summary.getDayDistance();
            crewTime += summary.getDatTime();
            ranks.add(RankEntity.builder().crewSummaryMapping(summary).build());
        }
        Collections.sort(ranks);
    }
}
