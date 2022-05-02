package com.darly.api.service.socket;

import com.darly.db.entity.socket.MsgRoom;
import com.darly.db.repository.socket.MsgRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 채팅방을 생성, 조회하고 하나의 세션에 메시지 발송을 하는 서비스
 * 채팅방 Map은 서버에 생성된 모든 채팅방의 정보를 모아둠
 * 채팅방의 정보저장은 일단은 HashMap에 저장하도록 구현(나중에 DB로 옮김)
 *
 * 채팅방 조회 : 채팅방 Map에 담긴 정보를 조회
 * 채팅방 생성 : Random UUID로 구별, ID를 가진 채팅방 객체를 생성하고 채팅방 Map에 추가
 * 메시지 발송 : 지정한 Websocket 세션에 메시지를 발송
 * */
@Service
@RequiredArgsConstructor
public class MsgService {
    @Autowired
    MsgRoomRepository msgRoomRepository;


    public List<MsgRoom> findAllRoom() {
        return msgRoomRepository.findAllRoom();
    }

    public MsgRoom findById(String roomId) {
        return msgRoomRepository.findById(roomId);
    }

    public MsgRoom createRoom(String name) {
        return msgRoomRepository.createRoom(name);
    }
}
