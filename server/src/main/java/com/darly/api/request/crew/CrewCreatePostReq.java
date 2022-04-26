package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("CrewCreatePostRequest")
public class CrewCreatePostReq {
    @ApiModelProperty(name="crewName", example="crewName1")
    private String crewName;
    @ApiModelProperty(name="crewDesc", example="crewDesc1")
    private String crewDesc;
    @ApiModelProperty(name="crewAddress", example="crewAddress")
    private Long crewAddress;
    @ApiModelProperty(name="crewJoin", example="crewJoin1")
    private String crewJoin;
}
