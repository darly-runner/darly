package com.darly.api.request.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("SectionRequest")
public class SectionReq {
    @ApiModelProperty(name="km", example="4.2")
    private Float km;
    @ApiModelProperty(name="pace", example="5.0")
    private Float pace;
    @ApiModelProperty(name="calories", example="100")
    private Integer calories;
}
