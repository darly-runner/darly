package com.darly.api.response.friend;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.User;
import com.darly.db.entity.address.AddressNameMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FriendProfileGetRes extends BaseResponseBody {

    private String userNickname;
    private String userMessage;
    private String userEmail;
    private List<AddressNameMapping> userAddresses;
    private Integer userPoint;
    private String userImage;
    private Float userTotalDistance;
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
