package com.darly.db.entity;

import com.darly.api.request.user.UserPatchReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public User(Long userId, String userNickname, String userEmail, String userMessage, Float userTotalDistance,
                Long userTotalTime, Integer userTotalHeart, Integer userTotalCalories, Float userTotalPace,
                Integer userHeartNum, Float userMinPace, Float userGoalDistance, Integer userGoalTime, Integer userPoint, String userImage) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userMessage = userMessage;
        this.userTotalDistance = userTotalDistance;
        this.userTotalTime = userTotalTime;
        this.userTotalHeart = userTotalHeart;
        this.userTotalCalories = userTotalCalories;
        this.userTotalPace = userTotalPace;
        this.userHeartNum = userHeartNum;
        this.userMinPace = userMinPace;
        this.userGoalDistance = userGoalDistance;
        this.userGoalTime = userGoalTime;
        this.userPoint = userPoint;
        this.userImage = userImage;
    }
}
