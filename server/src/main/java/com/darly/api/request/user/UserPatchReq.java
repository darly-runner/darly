package com.darly.api.request.user;

import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel("UserPatchRequest")
public class UserPatchReq {
    @ApiModelProperty(name="userNickname", example="김싸피")
    private String userNickname;
    @ApiModelProperty(name="userImage", example="userImageURL")
    private String userImage;
    @ApiModelProperty(name="userMessage", example="userMessage")
    private String userMessage;

    public static User ofPatch(User user, String userNickname, String userImage, String userMessage) {
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
                .userMessage(userMessage)
                .build();
    }

    @Builder
    public UserPatchReq(String userNickname, String userImage, String userMessage) {
        this.userNickname = userNickname;
        this.userImage = userImage;
        this.userMessage = userMessage;
    }
}
