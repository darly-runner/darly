package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel("UserStatsGetResponse")
public class UserStatsGetRes extends BaseResponseBody {
    @ApiModelProperty(name="userTotalDistance", example="100.00")
    private Float userTotalDistance;
    @ApiModelProperty(name="userTotalTime", example="10")
    private Long userTotalTime;
    @ApiModelProperty(name="userTotalHeart", example="80")
    private Integer userTotalHeart;
    @ApiModelProperty(name="userTotalCalories", example="1000")
    private Integer userTotalCalories;
    @ApiModelProperty(name="userTotalPace", example="10.00")
    private Float userTotalPace;
    @ApiModelProperty(name="userHeartNum", example="80")
    private Integer userHeartNum;
    @ApiModelProperty(name="userMinPace", example="7.00")
    private Float userMinPace;

    public static UserStatsGetRes of(User user, Integer statusCode, String message) {
        return UserStatsGetRes.builder()
                .statusCode(statusCode)
                .message(message)
                .userTotalDistance(user.getUserTotalDistance())
                .userTotalTime(user.getUserTotalTime())
                .userTotalHeart(user.getUserTotalHeart())
                .userTotalCalories(user.getUserTotalCalories())
                .userTotalPace(user.getUserTotalPace())
                .userHeartNum(user.getUserHeartNum())
                .userMinPace(user.getUserMinPace())
                .build();
    }

    @Builder
    public UserStatsGetRes(Integer statusCode, String message, Float userTotalDistance, Long userTotalTime, Integer userTotalHeart,
                           Integer userTotalCalories, Float userTotalPace, Integer userHeartNum, Float userMinPace) {
        super(statusCode, message);
        this.userTotalDistance = userTotalDistance;
        this.userTotalTime = userTotalTime;
        this.userTotalHeart = userTotalHeart;
        this.userTotalCalories = userTotalCalories;
        this.userTotalPace = userTotalPace;
        this.userHeartNum = userHeartNum;
        this.userMinPace = userMinPace;
    }
}
