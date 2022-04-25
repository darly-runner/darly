package com.darly.api.response.stat;

import com.darly.common.model.response.BaseResponseBody;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatWeekGetRes extends BaseResponseBody {
    private Float totalDistance;
    private Integer totalNum;
    private Float paceAvg;
    private Integer heartAvg;
    private Integer totalTime;
    private float[] distances;

    @Builder
    public StatWeekGetRes(Integer statusCode, String message, Float totalDistance, Integer totalNum, Float paceAvg, Integer heartAvg, Integer totalTime, float[] distances) {
        super(statusCode, message);
        this.totalDistance = totalDistance;
        this.totalNum = totalNum;
        this.paceAvg = paceAvg;
        this.heartAvg = heartAvg;
        this.totalTime = totalTime;
        this.distances = distances;
    }
}
