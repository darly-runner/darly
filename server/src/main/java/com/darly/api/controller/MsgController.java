package com.darly.api.controller;

import com.darly.db.entity.socket.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MsgController {
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/comm/message")
    public void message(Message message) {
        if (Message.MessageType.ENTER.equals(message.getMessageType())) {
            message.setMessage(message.getSender() + "이 입장했습니다.");
        }
        sendingOperations.convertAndSend("/sub/comm/room/" + message.getRoomId(), message);
    }
}
