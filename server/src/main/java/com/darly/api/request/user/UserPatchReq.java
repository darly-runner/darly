package com.darly.api.request.user;

import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
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
}
