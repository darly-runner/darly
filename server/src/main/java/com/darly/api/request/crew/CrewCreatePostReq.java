package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@ApiModel("CrewCreatePostRequest")
public class CrewCreatePostReq {
    @ApiModelProperty(name = "crewName", example = "crewName")
    private String crewName;
    @ApiModelProperty(name = "crewDesc", example = "crewDesc")
    private String crewDesc;
    @ApiModelProperty(name = "crewAddress", example = "1")
    private Long crewAddress;
    @ApiModelProperty(name = "crewJoin", example = "Free")
    private String crewJoin;
    @Nullable
    private MultipartFile crewImage;
}
