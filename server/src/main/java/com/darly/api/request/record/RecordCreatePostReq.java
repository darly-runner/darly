package com.darly.api.request.record;

import lombok.Getter;

import java.util.List;

@Getter
public class RecordCreatePostReq {
    private Long matchId;
    private Float recordDistance;
    private Float recordPace;
    private Integer recordCalories;
    private Integer recordHeart;
    private Float recordSpeed;
    private Long recordTime;
    private Integer recordRank;
    private Float recordStartLatitude;
    private Float recordStartLongitude;
    private Float recordEndLatitude;
    private Float recordEndLongitude;
    private List<String> coordinateLatitudes;
    private List<String> coordinateLongitudes;
    private List<SectionReq> sections;
}
