package com.darly.common.auth;

import com.darly.db.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Getter
public class SessionUser implements Serializable {
    private Long userId;
    private String userNickname;
    private String userEmail;
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

    public SessionUser(User user){
        this.userId= user.getUserId();
        this.userNickname = user.getUserNickname();
        this.userEmail = user.getUserEmail();
    }
}
