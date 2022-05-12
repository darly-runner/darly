package com.darly.api.controller;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.service.match.MatchService;
import com.darly.db.entity.socket.SocketMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MatchService matchService;

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    /*
    * ENTER : 유저 입장
    * LEAVE : 유저 퇴장
    * UPDATE : 방 정보 수정
    * READY : 유저 준비
    * UNREADY : 유저 준비 해제
    * START : 게임 시작
    * PACE : 페이스 측정
    * */
    @MessageMapping("/usermatch")
    public void userMatch(SocketMessage message) {
        // 유저 입장, SIGNAL만 보내고 그때마다 프론트에서 방입장 API 호출
        if (SocketMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getUserNickname() + "님이 입장했습니다.");
            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("ENTER sub 완료");
        }
        // 유저 퇴장, 신호 올떄마다 SIGNAL을 보내고 유저 삭제
        else if (SocketMessage.MessageType.LEAVE.equals(message.getType())) {

            // 나간 사람이 방장일때
            if(message.getIsHost() == 1) {
                message.setMessage("방장인 " + message.getUserNickname() + "님이 퇴장했습니다.");
            }
            else{
                message.setMessage(message.getUserNickname() + "님이 퇴장했습니다.");
            }

            Long userId = message.getUserId();
            Long matchId = message.getMatchId();
            matchService.matchOut(matchId, userId);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("LEAVE sub 완료");
        }
        // 방정보 업데이트
        else if (SocketMessage.MessageType.UPDATE.equals(message.getType())) {
            message.setMessage("방정보가 수정되었습니다.");

            Long matchId = message.getMatchId();
            MatchPatchReq matchPatchReq = MatchPatchReq.builder()
                    .matchTitle(message.getMatchTitle())
                    .matchMaxPerson(message.getMatchMaxPerson())
                    .matchGoalDistance(message.getMatchGoalDistance())
                    .build();
            
            matchService.patchMatchInfo(matchId, matchPatchReq);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("UPDATE sub 완료");
        }
        else if (SocketMessage.MessageType.READY.equals(message.getType())) {
            if(message.getIsReady().equals('R')) {
                message.setMessage(message.getUserNickname() + "님이 레디하셨습니다.");
            }
            else if (message.getIsReady().equals('N')) {
                message.setMessage(message.getUserNickname() + "님이 레디해제 하셨습니다.");
            }
            
            Long matchId = message.getMatchId();
            Long userId = message.getUserId();
            Character isReady = message.getIsReady();
            
            matchService.userReady(matchId, userId, isReady);
            
            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("READY sub 완료");
        }
        else if (SocketMessage.MessageType.START.equals(message.getType())) {
            message.setMessage("게임을 시작합니다.");

            // 방 상태는 시작으로 바꿔주고
            Long matchId = message.getMatchId();
            matchService.matchStart(matchId);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("START sub 완료");
        }

//        else if(socksdlkfjsadkljf euqls(ready)){
//            List<SockeMessage> <- 모든 사람들에 대한 가각의 message를 리스트형태로 넘겨줠ㅏ
//        }
    }
}
