package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("크루 공지 업데이트")
public class CrewUpdatePatchReq {
    @ApiModelProperty(name = "crewNotice", example = "crewNotice")
    private String crewNotice;
}
