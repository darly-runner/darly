package com.darly.api.request.user;

import com.darly.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserPatchRequest")
public class UserPatchReq {
    @ApiModelProperty(name="userNickname", example="김싸피")
    private String userNickname;
    @ApiModelProperty(name="userImage", example="userImageURL")
    private String userImage;

    public static User ofPatch(User user, String userNickname, String userImage) {
        return User.builder()
                .userId(user.getUserId())
                .userNickname(userNickname)
                .userEmail(user.getUserEmail())
                .userMessage(user.getUserMessage())
                .userTotalDistance(user.getUserTotalDistance())
                .userTotalTime(user.getUserTotalTime())
                .userTotalHeart(user.getUserTotalHeart())
                .userTotalCalories(user.getUserTotalCalories())
                .userTotalPace(user.getUserTotalPace())
                .userHeartNum(user.getUserHeartNum())
                .userMinPace(user.getUserMinPace())
                .userGoalDistance(user.getUserGoalDistance())
                .userGoalTime(user.getUserGoalTime())
                .userPoint(user.getUserPoint())
                .userImage(userImage)
                .build();
    }
}
