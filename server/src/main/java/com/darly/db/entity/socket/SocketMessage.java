package com.darly.db.entity.socket;

import com.darly.db.entity.match.MatchRUser;
import com.darly.db.entity.user.User;
import com.darly.db.entity.user.UserNowMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SocketMessage {

    // 메시지 타입
    public enum MessageType {
        CREATE,
        ENTER, LEAVE,
        READY,
        PACE,
        START, USER,
        RANDOMMATCH, RANDOMSTART,
        UPDATE
    }
    // 메시지 타입
    private MessageType type;

    // 기본적인 정보
    private Long userId;
    private Long matchId;
    private String userNickname;
    private Integer isHost; // 0이면 일반유저, 1이면 방장
    private Character isReady;
    
    // match 수정용
    private Long crewId;
    private String matchTitle;
    private Short matchMaxPerson;
    private Float matchGoalDistance;

    // 유저큐
    private List<MatchRUser> userQueue;

    // 방 상태
    private Character matchStatus;

    // 현재 방안의 유저들
    private List<UserNowMapping> users;

    // 경기 진행중의 정보
    private Float nowDistance;
    private Integer nowTime;
    private Integer nowPace;

    // 메시지
    private String message;
}
