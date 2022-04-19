package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
        UserStatsGetRes res = new UserStatsGetRes();

        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUserTotalDistance(user.getUserTotalDistance());
        res.setUserTotalTime(user.getUserTotalTime());
        res.setUserTotalHeart(user.getUserTotalHeart());
        res.setUserTotalCalories(user.getUserTotalCalories());
        res.setUserTotalPace(user.getUserTotalPace());
        res.setUserHeartNum(user.getUserHeartNum());
        res.setUserMinPace(user.getUserMinPace());

        return res;
    }
}
