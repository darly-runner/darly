package com.darly.api.response.stat;

import com.darly.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("StatAllGetResponse")
public class StatAllGetRes extends BaseResponseBody {
    @ApiModelProperty(name="totalDistance", example="10.0")
    private Float totalDistance;
    @ApiModelProperty(name="totalNum", example="0")
    private Integer totalNum;
    @ApiModelProperty(name="paceAvg", example="5.0")
    private Float paceAvg;
    @ApiModelProperty(name="heartAvg", example="80")
    private Integer heartAvg;
    @ApiModelProperty(name="totalTime", example="10")
    private Integer totalTime;
    @ApiModelProperty(name="startYear", example="2022")
    private Integer startYear;
    @ApiModelProperty(name="distances", example="1.0, 1.1, 1.5")
    private float[] distances;

    @Builder
    public StatAllGetRes(Integer statusCode, String message, Float totalDistance, Integer totalNum, Float paceAvg, Integer heartAvg, Integer totalTime, Integer startYear, float[] distances) {
        super(statusCode, message);
        this.totalDistance = totalDistance;
        this.totalNum = totalNum;
        this.paceAvg = paceAvg;
        this.heartAvg = heartAvg;
        this.totalTime = totalTime;
        this.distances = distances;
        this.startYear = startYear;
    }
}