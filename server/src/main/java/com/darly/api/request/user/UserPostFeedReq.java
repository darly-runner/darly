package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Nullable;
import lombok.Setter;


@Getter
@Setter
@ApiModel("UserPostFeedRequest")
public class UserPostFeedReq {
    @Nullable
    @ApiModelProperty(name="userFeedImage", example="string")
    private MultipartFile userFeedImage;
}
