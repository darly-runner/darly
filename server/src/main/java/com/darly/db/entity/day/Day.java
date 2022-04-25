package com.darly.db.entity.day;

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
@Table(name = "tb_day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;
    private Long userId;
    private Long dayDate;
    private Float dayDistance;
    private Long dayTime;
    private Integer dayNum;
    private Float dayPace;
}
