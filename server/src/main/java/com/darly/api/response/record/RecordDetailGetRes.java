package com.darly.api.response.record;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.MatchResultMapping;
import com.darly.db.entity.record.Coordinate;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.record.SectionMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("RecordDetailGetResponse")
public class RecordDetailGetRes extends BaseResponseBody {
    @ApiModelProperty(name="userImage", example="userImage.png")
    private String userImage;
    @ApiModelProperty(name="recordId", example="1")
    private Long recordId;
    @ApiModelProperty(name="recordTitle", example="recordTitle1")
    private String recordTitle;
    @ApiModelProperty(name="recordDate", example="recordDate")
    private String recordDate;
    @ApiModelProperty(name="recordDistance", example="10.0")
    private Float recordDistance;
    @ApiModelProperty(name="recordTime", example="5")
    private Long recordTime;
    @ApiModelProperty(name="recordPace", example="5.0")
    private Integer recordPace;
    @ApiModelProperty(name="recordHeart", example="0")
    private Integer recordHeart;
    @ApiModelProperty(name="recordSpeed", example="0")
    private Float recordSpeed;
    @ApiModelProperty(name="recordCalories", example="100")
    private Integer recordCalories;
    @ApiModelProperty(name="recordImage", example="image.png")
    private String recordImage;
    @ApiModelProperty(name="coordinateLatitudes", example="[string1,string2,string3]")
    private String[] coordinateLatitudes;
    @ApiModelProperty(name="coordinateLongitudes", example="[string1,string2,string3]")
    private String[] coordinateLongitudes;
    @ApiModelProperty(name="sections", example="[section1,section2,section3]")
    private List<SectionMapping> sections;
    @ApiModelProperty(name="recordRank", example="1")
    private Integer recordRank;
    @ApiModelProperty(name="ranks", example="[rank1, rank2, rank3]")
    private List<MatchResultMapping> ranks;

    @Builder
    public RecordDetailGetRes(Integer statusCode, String message, String userImage, Record record, Coordinate coordinate, List<SectionMapping> sections, List<MatchResultMapping> ranks) {
        super(statusCode, message);
        this.userImage = userImage;
        this.recordId = record.getRecordId();
        this.recordTitle = record.getRecordTitle();
        this.recordDate = new SimpleDateFormat("yyyy/MM/dd a KK:mm").format(new Date(record.getRecordDate() * 1000));
        this.recordDistance = record.getRecordDistance();
        this.recordTime = record.getRecordTime();
        this.recordPace = record.getRecordPace();
        this.recordHeart = record.getRecordHeart();
        this.recordSpeed = record.getRecordSpeed();
        this.recordCalories = record.getRecordCalories();
        this.recordImage = record.getRecordImage();
        this.sections = sections;
        this.coordinateLatitudes = coordinate == null ? null : coordinate.getCoordinateLatitude().split(",");
        this.coordinateLongitudes = coordinate == null ? null : coordinate.getCoordinateLongitude().split(",");
        this.recordRank = record.getRecordRank();
        this.ranks = ranks;
    }
}
