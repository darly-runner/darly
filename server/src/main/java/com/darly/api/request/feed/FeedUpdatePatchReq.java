package com.darly.api.request.feed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ApiModel("FeedUpdatePatchRequest")
public class FeedUpdatePatchReq {
    @ApiModelProperty(name = "feedTitle", example = "feedTitle")
    @Nullable
    private String feedTitle;
    @ApiModelProperty(name = "feedContent", example = "feedContent")
    @Nullable
    private String feedContent;
    @ApiModelProperty(name = "feedImage", example = "[feedImage.png, feedImage.png]")
    @Nullable
    private MultipartFile feedImage;
}
