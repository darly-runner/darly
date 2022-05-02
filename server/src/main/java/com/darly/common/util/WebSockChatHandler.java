package com.darly.common.util;

import com.darly.api.service.socket.MsgService;
import com.darly.db.entity.socket.Message;
import com.darly.db.entity.socket.MsgRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSockChatHandler extends TextWebSocketHandler{

    private final MsgService msgService;
    private final ObjectMapper objectMapper;

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//
//        /* 웹소켓 클라이언트로부터 메시지를 전달받아 Message 객체로 변환
//        *  전달받은 Message 객체에 담긴 roomId로 발송 대상 채팅방 정보를 조회
//        *  해당 채팅방에 입장해 있는 모든 클라이언트들(Set<WebSocketSession>)에게 타입에 맞는 메시지를 전송
//        * */
//        Message msg = objectMapper.readValue(payload, Message.class);
//        System.out.println(msg);
//        System.out.println(msg.getRoomId());
//        MsgRoom room = msgService.findById(msg.getRoomId());
//        System.out.println(room);
//        room.handleActions(session, msg, msgService);
//    }
}
