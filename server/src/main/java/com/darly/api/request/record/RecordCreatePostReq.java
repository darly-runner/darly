package com.darly.api.request.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ApiModel("RecordCreatePostRequest")
public class RecordCreatePostReq {
    @ApiModelProperty(name = "matchId", example = "1")
    private Long matchId;
    @ApiModelProperty(name = "recordDistance", example = "10.0")
    private Float recordDistance;
    @ApiModelProperty(name = "recordPace", example = "6")
    private Integer recordPace;
    @ApiModelProperty(name = "recordCalories", example = "0")
    private Integer recordCalories;
    @ApiModelProperty(name = "recordHeart", example = "0")
    private Integer recordHeart;
    @ApiModelProperty(name = "recordSpeed", example = "5")
    private Float recordSpeed;
    @ApiModelProperty(name = "recordTime", example = "10")
    private Long recordTime;
    @ApiModelProperty(name = "recordTitle", example = "recordTitle")
    @Nullable
    private String recordTitle;
    @ApiModelProperty(name = "recordRank", example = "1")
    private Integer recordRank;
    @Nullable
    private MultipartFile recordImage;
    @ApiModelProperty(name = "coordinateLatitudes", example = "[0.0, 1.1, 2.2]")
    private List<String> coordinateLatitudes;
    @ApiModelProperty(name = "coordinateLongitudes", example = "[0.0, 1.1, 2.2]")
    private List<String> coordinateLongitudes;
    @ApiModelProperty(name = "sections", example = "[1,2,3]")
    private List<SectionReq> sections;
}
