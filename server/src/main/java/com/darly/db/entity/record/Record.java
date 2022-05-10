package com.darly.db.entity.record;

import com.darly.db.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private Long dayId;
    private Long matchId;
    private String recordTitle;
    private Long recordDate;
    private Float recordDistance;
    private Long recordTime;
    private Integer recordPace;
    private Float recordSpeed;
    private Integer recordHeart;
    private Integer recordCalories;
    private Integer recordRank;
    private String recordImage;
}
