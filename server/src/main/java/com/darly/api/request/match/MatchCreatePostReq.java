package com.darly.api.request.match;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("MatchCreatePostReq")
public class MatchCreatePostReq {
    @ApiModelProperty(name="matchTitle", example="matchTitle")
    private String matchTitle;
    @ApiModelProperty(name="matchMaxPerson", example="6")
    private Short matchMaxPerson;
    @ApiModelProperty(name="matchGoalDistance", example="10")
    private Float matchGoalDistance;
}
