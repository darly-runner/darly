package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserPatchConditionRequest")
public class UserPatchConditionReq {
    @ApiModelProperty(name="userGoalDistance", example="4.02")
    private Float userGoalDistance;
    @ApiModelProperty(name="userGoalTime", example="10")
    private Integer userGoalTime;
}
