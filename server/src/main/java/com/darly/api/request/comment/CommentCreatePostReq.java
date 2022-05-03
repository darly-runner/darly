package com.darly.api.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@ApiModel("CommentCreatePostRequest")
public class CommentCreatePostReq {
    @ApiModelProperty(name = "commentContent", example = "commentContent")
    private String commentContent;
}
