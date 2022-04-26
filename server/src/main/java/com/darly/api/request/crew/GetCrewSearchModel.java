package com.darly.api.request.crew;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("GetCrewSerachModel")
public class GetCrewSearchModel {

    @ApiModelProperty(name="page", example="1")
    private Integer page;
    @ApiModelProperty(name="address", example="address")
    private Integer address;
    @ApiModelProperty(name="key", example="key1")
    private String key;

    public GetCrewSearchModel(Integer page, Integer address) {
        this.page = page;
        this.address = address;
        this.key = "";
    }
}
