package com.darly.api.response.record;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.match.MatchResultMapping;
import com.darly.db.entity.record.Coordinate;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.record.SectionMapping;
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
public class RecordDetailGetRes extends BaseResponseBody {
    private Long recordId;
    private String recordTitle;
    private String recordDate;
    private Float recordDistance;
    private Long recordTime;
    private Float recordPace;
    private Integer recordHeart;
    private Integer recordCalories;
    private Float recordStartLatitude;
    private Float recordStartLongitude;
    private Float recordEndLatitude;
    private Float recordEndLongitude;
    private String[] coordinateLatitudes;
    private String[] coordinateLongitudes;
    private List<SectionMapping> sections;
    private Integer recordRank;
    private List<MatchResultMapping> ranks;

    @Builder
    public RecordDetailGetRes(Integer statusCode, String message, Record record, Coordinate coordinate, List<SectionMapping> sections, List<MatchResultMapping> ranks) {
        super(statusCode, message);
        this.recordId = record.getRecordId();
        this.recordTitle = record.getRecordTitle();
        this.recordDate = new SimpleDateFormat("yyyy/MM/dd a KK:mm").format(new Date(record.getRecordDate() * 1000));
        this.recordDistance = record.getRecordDistance();
        this.recordTime = record.getRecordTime();
        this.recordPace = record.getRecordPace();
        this.recordHeart = record.getRecordHeart();
        this.recordCalories = record.getRecordCalories();
        this.recordStartLatitude = record.getRecordStartLatitude();
        this.recordStartLongitude = record.getRecordStartLongitude();
        this.recordEndLatitude = record.getRecordEndLatitude();
        this.recordEndLongitude = record.getRecordEndLongitude();
        this.sections = sections;
        this.coordinateLatitudes = coordinate == null ? null : coordinate.getCoordinateLatitude().split(",");
        this.coordinateLongitudes = coordinate == null ? null : coordinate.getCoordinateLongitude().split(",");
        this.recordRank = record.getRecordRank();
        this.ranks = ranks;
    }
}
