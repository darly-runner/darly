package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserGetResponse")
public class UserGetRes extends BaseResponseBody {
    @ApiModelProperty(name="userNickname", example="김싸피")
    private String userNickname;
    @ApiModelProperty(name="userEmail", example="ssafy@ssafy.com")
    private String userEmail;
    @ApiModelProperty(name="userPoint", example="0")
    private Integer userPoint;
    @ApiModelProperty(name="userImage", example="userImageURL")
    private String userImage;
    @ApiModelProperty(name="userMessage", example="상태메시지")
    private String userMessage;

    public static UserGetRes of(User user, Integer statusCode, String message) {
       UserGetRes res = new UserGetRes();

       res.setStatusCode(statusCode);
       res.setMessage(message);
       res.setUserNickname(user.getUserNickname());
       res.setUserEmail(user.getUserEmail());
       res.setUserPoint(user.getUserPoint());
       res.setUserImage(user.getUserImage());
       res.setUserMessage(user.getUserMessage());

       return res;
    }
}
