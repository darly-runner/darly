package com.darly.db.entity.record;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
public class RecordMapping {
    private Long recordId;
    private String recordTitle;
    private String recordDate;
    private Float recordDistance;
    private Long recordTime;
    private Integer recordPace;
    private Integer recordHeart;
    private Integer recordCalories;
    private String recordImage;
    @Nullable
    private String[] coordinateLatitudes;
    @Nullable
    private String[] coordinateLongitudes;

    @QueryProjection
    public RecordMapping(Long recordId, String recordTitle, Long recordDate, Float recordDistance, Long recordTime, Integer recordPace, Integer recordHeart, Integer recordCalories, String recordImage) {
        this.recordId = recordId;
        this.recordTitle = recordTitle;
        this.recordDate =  new SimpleDateFormat("yyyy/MM/dd").format(new Date(recordDate * 1000));
        this.recordDistance = recordDistance;
        this.recordTime = recordTime;
        this.recordPace = recordPace;
        this.recordHeart = recordHeart;
        this.recordCalories = recordCalories;
        this.recordImage = recordImage;
    }

    @QueryProjection
    public RecordMapping(Long recordId, String recordTitle, Long recordDate, Float recordDistance, Long recordTime, Integer recordPace, Integer recordHeart, Integer recordCalories, String recordImage, String coordinateLatitudes, String coordinateLongitudes) {
        this.recordId = recordId;
        this.recordTitle = recordTitle;
        this.recordDate =  new SimpleDateFormat("yyyy/MM/dd").format(new Date(recordDate * 1000));
        this.recordDistance = recordDistance;
        this.recordTime = recordTime;
        this.recordPace = recordPace;
        this.recordHeart = recordHeart;
        this.recordCalories = recordCalories;
        this.recordImage = recordImage;
        this.coordinateLatitudes = coordinateLatitudes.split(",");
        this.coordinateLongitudes = coordinateLongitudes.split(",");
    }
}
