package com.darly.db.repository.socket;

import com.darly.db.entity.socket.MsgRoom;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Repository
public class MsgRoomRepository {
    private Map<String, MsgRoom> msgRoomMap;

    @PostConstruct
    private void init() {
        msgRoomMap = new LinkedHashMap<>();
    }

    public List<MsgRoom> findAllRoom() {
        List<MsgRoom> msgRooms = new ArrayList(msgRoomMap.values());
        Collections.reverse(msgRooms);
        return msgRooms;
    }

    public MsgRoom findById(String roomId) {
        return msgRoomMap.get(roomId);
    }

    public MsgRoom createRoom(String name) {
        MsgRoom room = MsgRoom.builder().roomId(name).build();
        msgRoomMap.put(room.getRoomId(), room);
        return room;
    }

}
