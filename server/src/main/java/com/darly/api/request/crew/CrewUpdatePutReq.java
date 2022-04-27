package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel("크루 상세정보 업데이트")
public class CrewUpdatePutReq {
    @ApiModelProperty(name = "crewName", example = "crewName")
    @Nullable
    private String crewName;
    @ApiModelProperty(name = "crewDesc", example = "crewDesc")
    @Nullable
    private String crewDesc;
    @ApiModelProperty(name = "crewAddress", example = "1")
    @Nullable
    private Long crewAddress;
    @ApiModelProperty(name = "crewJoin", example = "Free")
    @Nullable
    private String crewJoin;
    @ApiModelProperty(name = "crewImage", example = "image.png (file)")
    @Nullable
    private MultipartFile crewImage;
}
