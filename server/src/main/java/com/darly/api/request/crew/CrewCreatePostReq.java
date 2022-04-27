package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ApiModel("CrewCreatePostRequest")
public class CrewCreatePostReq {
    @ApiModelProperty(name="crewName", example="crewName1")
    private String crewName;
    @ApiModelProperty(name="crewDesc", example="crewDesc1")
    private String crewDesc;
    @ApiModelProperty(name="crewAddress", example="1")
    private Long crewAddress;
    @ApiModelProperty(name="crewJoin", example="crewJoin1")
    private String crewJoin;
    @Nullable
    private MultipartFile crewImage;
}
