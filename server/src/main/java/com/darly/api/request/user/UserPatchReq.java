package com.darly.api.request.user;

import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ApiModel("UserPatchRequest")
public class UserPatchReq {
    @ApiModelProperty(name = "userNickname", example = "김싸피")
    private String userNickname;
    @ApiModelProperty(name = "userImage", example = "userImage.png")
    @Nullable
    private MultipartFile userImage;
    @ApiModelProperty(name = "userMessage", example = "userMessage")
    private String userMessage;
    @ApiModelProperty(name = "userAddresses", example = "[1, 2]")
    private List<Long> userAddresses;

    public static User ofPatch(User user, String userNickname, String userImage, String userMessage) {
        if (userNickname.length() == 0)
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
                    .userGoalDistance(user.getUserGoalDistance())
                    .userGoalTime(user.getUserGoalTime())
                    .userPoint(user.getUserPoint())
                    .userImage(userImage)
                    .userMessage(userMessage)
                    .build();
        else
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
    public UserPatchReq(String userNickname, MultipartFile userImage, String userMessage, List<Long> userAddresses) {
        this.userNickname = userNickname;
        this.userImage = userImage;
        this.userMessage = userMessage;
        this.userAddresses = userAddresses;
    }
}
