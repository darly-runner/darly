package com.darly.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public User() {
    }

    @Builder
    public User(Long id, String name, String email) {
        this.userId = id;
        this.userNickname = name;
        this.userEmail = email;
    }

    public User update(String name){
        this.userNickname = name;
        return this;
    }
}
