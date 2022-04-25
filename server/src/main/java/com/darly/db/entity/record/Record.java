package com.darly.db.entity.record;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "tb_record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    private Long userId;
    private Long dayId;
    private Long matchId;
    private String recordTitle;
    private Long recordDate;
    private Float recordDistance;
    private Long recordTime;
    private Float recordPace;
    private Float recordSpeed;
    private Integer recordHeart;
    private Float recordStartLatitude;
    private Float recordStartLongitude;
    private Float recordEndLatitude;
    private Float recordEndLongitude;
    private Integer recordCalories;
    private Integer recordRank;
}
