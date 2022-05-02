package com.darly.db.entity.socket;

import lombok.*;

import javax.persistence.*;


/**
 * 채팅방은 현재 방에 입장한 클라이언트의 Session 정보 가져와야 함
 * 채팅방 id
 * 채팅방에는 입장/통신 기능이 있으므로 handleAction 통해 분기 처리
 * 입장시에 채팅방의 session 정보 리스트에 클라이언트의 session을 추가해놓고, 채팅방에 미시지가 도착할 경우 채팅방의 모든 session에
 * 메시지륿 발송하면 됨
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MsgRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;
}
