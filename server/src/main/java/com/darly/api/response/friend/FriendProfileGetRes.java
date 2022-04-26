package com.darly.api.response.friend;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.user.User;
import com.darly.db.entity.address.AddressNameMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("FriendProfileGetResponse")
public class FriendProfileGetRes extends BaseResponseBody {
    @ApiModelProperty(name="userNickname", example="닉네임1")
    private String userNickname;
    @ApiModelProperty(name="userMessage", example="메시지1")
    private String userMessage;
    @ApiModelProperty(name="userEmail", example="ssafy@ssafy.com")
    private String userEmail;
    @ApiModelProperty(name="userAddresses", example="[address1, address2, address3]")
    private List<AddressNameMapping> userAddresses;
    @ApiModelProperty(name="userPoint", example="0")
    private Integer userPoint;
    @ApiModelProperty(name="userImage", example="userImg1")
    private String userImage;
    @ApiModelProperty(name="userTotalDistance", example="10.0")
    private Float userTotalDistance;
    @ApiModelProperty(name="userFriendNum", example="0")
    private Long userFriendNum;


    public static FriendProfileGetRes of(Integer statusCode, String message, User user, List<AddressNameMapping> addresses, Long friendNum){
        return FriendProfileGetRes.builder()
                .statusCode(statusCode)
                .message(message)
                .userNickname(user.getUserNickname())
                .userMessage(user.getUserMessage())
                .userEmail(user.getUserEmail())
                .userPoint(user.getUserPoint())
                .userImage(user.getUserImage())
                .userTotalDistance(user.getUserTotalDistance())
                .userAddresses(addresses)
                .userFriendNum(friendNum)
                .build();
    }

    @Builder
    public FriendProfileGetRes(Integer statusCode, String message, String userNickname, String userMessage, String userEmail, List<AddressNameMapping> userAddresses, Integer userPoint, String userImage, Float userTotalDistance, Long userFriendNum) {
        super(statusCode, message);
        this.userNickname = userNickname;
        this.userMessage = userMessage;
        this.userEmail = userEmail;
        this.userAddresses = userAddresses;
        this.userPoint = userPoint;
        this.userImage = userImage;
        this.userTotalDistance = userTotalDistance;
        this.userFriendNum = userFriendNum;
    }
}
