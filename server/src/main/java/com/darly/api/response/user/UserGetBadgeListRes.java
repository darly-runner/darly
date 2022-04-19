package com.darly.api.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserGetBadgeListResponse")
public class UserGetBadgeListRes {
    @ApiModelProperty(name="badgeName", example="badge1")
    private String badgeName;
    @ApiModelProperty(name="badgeImage", example="badgeImg1")
    private String badgeImage;
    @ApiModelProperty(name="badgeCondition", example="condition1")
    private String badgeCondition;
}
