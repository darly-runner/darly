package com.darly.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
// null인 값은 제외하고 쿼리문이 만들어짐
@DynamicInsert
@Table(name="tb_user")
public class User {

    // PK로 지정정
    @Id
    // 기본 키 자동 생성 = Auto Increase
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    private String userNickname;
    private String userEmail;
    private String userMessage;
    private Float userTotalDistance;
    private Long userTotalTime;
    private Integer userTotalHeart;
    private Integer userTotalCalories;
    private Float userTotalPace;
    private Integer userHeartNum;
    private Float userMinPace;
    private Float userGoalDistance;
    private Integer userGoalTime;
    private Integer userPoint;
    private String userImage;
}
