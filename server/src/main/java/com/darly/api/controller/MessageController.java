package com.darly.api.controller;
import com.darly.db.entity.socket.SocketMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    @MessageMapping("/usermatch")
    public void userMatch(SocketMessage message) {
        if (SocketMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage("누군가가 입장했습니다.");
            template.convertAndSend("/sub/usermatch/" + message.getUserId(), message);
        }
//        else if(socksdlkfjsadkljf euqls(ready)){
//            List<SockeMessage> <- 모든 사람들에 대한 가각의 message를 리스트형태로 넘겨줠ㅏ
//        }
    }
}
