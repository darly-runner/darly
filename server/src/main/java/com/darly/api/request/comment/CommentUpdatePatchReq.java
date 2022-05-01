package com.darly.api.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("CommentUpdatePatchRequest")
public class CommentUpdatePatchReq {
    @ApiModelProperty(name = "commentContent", example = "commentContent")
    private String commentContent;
}
