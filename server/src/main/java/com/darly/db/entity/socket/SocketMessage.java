package com.darly.db.entity.socket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Getter
@Setter
public class SocketMessage {

    // 메시지 타입
    public enum MessageType {
        // 유저 세션에 넣을 용
        LOGIN, LOGOUT,		// 로그인 ,로그아웃

        // [지정큐]겨루기 초대
        INVITE,				// 초대
        ACCEPT, 			// 수락
        REFUSE,				// 수락
        // [지정큐]대기방
        ENTER, LEAVE,		// 대기방 입장, 떠남
        GAMESTART,			// 겨루기 시작

        // [랜덤큐]
        RandomMatched
    }
    // 일반 로그인
    private MessageType type; // 메시지 타입
    private String userId;
    // 겨루기 신청
    private String hostId;
    private String guestId;
    private String roomId; // 방번호
    // for 메시지 추가정보
    private String userNick;
    private String hostNick;
    private String guestNick;
    // 랜덤큐
    private String userTier;
    private String isMatched;
    private String isHost;
    // 게임시작
    private String ovToken;

    private String message; // 메시지
}
