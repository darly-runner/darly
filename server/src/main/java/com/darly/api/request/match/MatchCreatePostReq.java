package com.darly.api.request.match;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ApiModel("MatchCreatePostReq")
public class MatchCreatePostReq implements Serializable {
    @ApiModelProperty(name="matchTitle", example="matchTitle")
    private String matchTitle;
    @ApiModelProperty(name="matchMaxPerson", example="6")
    private Short matchMaxPerson;
    @ApiModelProperty(name="matchGoalDistance", example="10")
    private Float matchGoalDistance;
}
