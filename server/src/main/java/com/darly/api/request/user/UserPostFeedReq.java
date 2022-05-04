package com.darly.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel("UserPostFeedRequest")
public class UserPostFeedReq {
    @ApiModelProperty(name="userFeedImage", example="string")
    private MultipartFile userFeedImage;
}
