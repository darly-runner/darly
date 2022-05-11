package com.darly.db.entity.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocketMessage {

    // 메시지 타입
    public enum MessageType {
        ENTER, LEAVE,
        READY,
        PACE,
        START
    }
    // 메시지 타입
    private MessageType type;

    private Long userId;
    private Long matchId;
    private String userNickname;

    // 메시지
    private String message;
}
