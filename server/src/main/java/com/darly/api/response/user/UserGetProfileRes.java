package com.darly.api.response.user;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.address.Address;
import com.darly.db.entity.address.AddressIdAndNameMapping;
import com.darly.db.entity.address.AddressNameMapping;
import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel("UserGetResponse")
public class UserGetProfileRes extends BaseResponseBody {
    @ApiModelProperty(name = "userNickname", example = "김싸피")
    private String userNickname;
    @ApiModelProperty(name = "userMessage", example = "김싸피")
    private String userMessage;
    @ApiModelProperty(name = "userEmail", example = "ssafy@ssafy.com")
    private String userEmail;
    @ApiModelProperty(name = "userPoint", example = "0")
    private Integer userPoint;
    @ApiModelProperty(name = "userImage", example = "userImageURL")
    private String userImage;
    @ApiModelProperty(name = "userAddresses", example = "[name, name2, name3]")
    private List<AddressIdAndNameMapping> userAddresses;
    @ApiModelProperty(name = "userTotalDistance", example = "0.0")
    private Float userTotalDistance;
    @ApiModelProperty(name = "userFriendNum", example = "0")
    private Long userFriendNum;

    @Builder
    public UserGetProfileRes(Integer statusCode, String message, User user, List<AddressIdAndNameMapping> addressList, Long userFriendNum) {
        super(statusCode, message);
        this.userNickname = user.getUserNickname();
        this.userMessage = user.getUserMessage();
        this.userEmail = user.getUserEmail();
        this.userPoint = user.getUserPoint();
        this.userImage = user.getUserImage();
        this.userAddresses = addressList;
        this.userTotalDistance = user.getUserTotalDistance();
        this.userFriendNum = userFriendNum;
    }
}
