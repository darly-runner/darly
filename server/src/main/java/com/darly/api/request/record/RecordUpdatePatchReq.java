package com.darly.api.request.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("RecordUpdatePatchRequest")
public class RecordUpdatePatchReq {
    @ApiModelProperty(name="recordTitle", example="title")
    private String recordTitle;
}
