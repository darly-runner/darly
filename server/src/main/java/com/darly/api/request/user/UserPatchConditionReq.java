package com.darly.api.request.user;

import com.darly.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@ApiModel("UserPatchConditionRequest")
public class UserPatchConditionReq {
    @ApiModelProperty(name="userGoalDistance", example="4.02")
    private Float userGoalDistance;
    @ApiModelProperty(name="userGoalTime", example="10")
    private Integer userGoalTime;

    public static User ofPatchCondition(User user, Float userGoalDistance, Integer userGoalTime) {
        return User.builder()
                .userId(user.getUserId())
                .userNickname(user.getUserNickname())
                .userEmail(user.getUserEmail())
                .userMessage(user.getUserMessage())
                .userTotalDistance(user.getUserTotalDistance())
                .userTotalTime(user.getUserTotalTime())
                .userTotalHeart(user.getUserTotalHeart())
                .userTotalCalories(user.getUserTotalCalories())
                .userTotalPace(user.getUserTotalPace())
                .userHeartNum(user.getUserHeartNum())
                .userMinPace(user.getUserMinPace())
                .userGoalDistance(userGoalDistance)
                .userGoalTime(userGoalTime)
                .userPoint(user.getUserPoint())
                .userImage(user.getUserImage())
                .build();
    }

    @Builder
    public UserPatchConditionReq(Float userGoalDistance, Integer userGoalTime) {
        this.userGoalDistance = userGoalDistance;
        this.userGoalTime = userGoalTime;
    }
}
