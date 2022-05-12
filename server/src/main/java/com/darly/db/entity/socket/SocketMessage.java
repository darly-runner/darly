package com.darly.db.entity.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketMessage {

    // 메시지 타입
    public enum MessageType {
        ENTER, LEAVE,
        READY, UNREADY,
        PACE,
        START,
        UPDATE
    }
    // 메시지 타입
    private MessageType type;

    // 기본적인 정보
    private Long userId;
    private Long matchId;
    private String userNickname;
    private Integer isHost;

    // match 수정용
    private String matchTitle;
    private Short matchMaxPerson;
    private Float matchGoalDistance;

    // 메시지
    private String message;
}
