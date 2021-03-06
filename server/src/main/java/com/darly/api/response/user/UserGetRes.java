package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel("UserGetResponse")
public class UserGetRes extends BaseResponseBody {
    @ApiModelProperty(name="userId", example="1")
    private Long userId;
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
       return UserGetRes.builder()
               .statusCode(statusCode)
               .message(message)
               .userNickname(user.getUserNickname())
               .userEmail(user.getUserEmail())
               .userPoint(user.getUserPoint())
               .userImage(user.getUserImage())
               .userMessage(user.getUserMessage())
               .userId(user.getUserId())
               .build();
    }

    @Builder
    public UserGetRes(Integer statusCode, Long userId, String message, String userNickname, String userEmail, Integer userPoint, String userImage, String userMessage) {
        super(statusCode, message);
        this.userId = userId;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userPoint = userPoint;
        this.userImage = userImage;
        this.userMessage = userMessage;
    }
}
