package com.darly.api.request.feed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ApiModel("FeedCreatePostRequest")
public class FeedCreatePostReq {
    @ApiModelProperty(name = "feedTitle", example = "feedTitle")
    private String feedTitle;
    @ApiModelProperty(name = "feedContent", example = "feedContent")
    private String feedContent;
    @ApiModelProperty(name = "feedImage", example = "[feedImage.png, feedImage.png]")
    private MultipartFile feedImage;
}
