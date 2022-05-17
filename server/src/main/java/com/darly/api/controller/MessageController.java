package com.darly.api.controller;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.service.crew.CrewService;
import com.darly.api.service.crew.UserCrewService;
import com.darly.api.service.match.MatchService;
import com.darly.api.service.match.UserMatchService;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchRUser;
import com.darly.db.entity.socket.SocketMessage;
import com.darly.db.entity.user.UserNowMapping;
import com.darly.db.entity.user.UserNowPace;
import com.darly.db.repository.match.UserMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MatchService matchService;
    private final CrewService crewService;
    private final UserCrewService userCrewService;
    private final UserMatchService userMatchService;
    private final UserMatchRepository userMatchRepository;

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    @MessageMapping("/creatematch")
    public void createMatch(SocketMessage message) {
        message.setMessage("방이 생성되었습니다.");

        Long userId = message.getUserId();

        // 넘어온 방정보를 저장
        Long crewId = message.getCrewId();
        String matchTitle = message.getMatchTitle();
        Float matchGoalDistance = message.getMatchGoalDistance();
        Short matchMaxPerson = message.getMatchMaxPerson();

        MatchCreatePostReq matchCreatePostReq = MatchCreatePostReq.builder()
                .matchTitle(matchTitle)
                .matchGoalDistance(matchGoalDistance)
                .matchMaxPerson(matchMaxPerson)
                .build();

        if (!crewService.isCrewExists(crewId)) {
            message.setMessage("Fail create crew match: Not valid crewId");
        } else if (!userCrewService.isUserCrewExists(userId, crewId)) {
            message.setMessage("Fail create crew match: User is not member");
        } else {
            Match match = matchService.createCrewMatch(crewId, userId, matchCreatePostReq);
            userMatchService.createUserMatch(userId, match.getMatchId());
            message.setMatchId(match.getMatchId());

            message.setMessage("Success create crew match");
            template.convertAndSend("/sub/creatematch", message);
            System.out.println("CREATE sub 완료");
        }
    }

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
            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("ENTER sub 완료");
        }
        // 유저 퇴장, 신호 올떄마다 SIGNAL을 보내고 유저 삭제
        else if (SocketMessage.MessageType.LEAVE.equals(message.getType())) {
            Long userId = message.getUserId();
            Long matchId = message.getMatchId();

            message.setMatchStatus(matchService.matchOut(matchId, userId));

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("LEAVE sub 완료");
        }
        // 방정보 업데이트
        else if (SocketMessage.MessageType.UPDATE.equals(message.getType())) {
            Long matchId = message.getMatchId();
            MatchPatchReq matchPatchReq = MatchPatchReq.builder()
                    .matchTitle(message.getMatchTitle())
                    .matchMaxPerson(message.getMatchMaxPerson())
                    .matchGoalDistance(message.getMatchGoalDistance())
                    .build();

            matchService.patchMatchInfo(matchId, matchPatchReq);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("UPDATE sub 완료");
        } else if (SocketMessage.MessageType.READY.equals(message.getType())) {
            Long matchId = message.getMatchId();
            Long userId = message.getUserId();
            Character isReady = message.getIsReady();

            matchService.userReady(matchId, userId, isReady);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("READY sub 완료");
        } else if (SocketMessage.MessageType.START.equals(message.getType())) {
            message.setMessage("게임을 시작합니다.");

            // 방 상태는 시작으로 바꿔주고
            Long matchId = message.getMatchId();
            matchService.matchStart(matchId);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
            System.out.println("START sub 완료");
        } else if (SocketMessage.MessageType.RANDOMMATCH.equals(message.getType())) {
            message.setMessage("랜덤매칭을 시작합니다.");

            Long userId = message.getUserId();
            List<MatchRUser> userQueue = matchService.randomMatch(userId);

            if (userQueue == null) {
                return;
            } else {
                message.setUserQueue(userQueue);
            }

            template.convertAndSend("/sub/usermatch/randommatch", message);
        } else if (SocketMessage.MessageType.USER.equals(message.getType())) {
            Long matchId = message.getMatchId();

            List<UserNowPace> users = matchService.nowUsers(matchId);

            message.setUsers(users);

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
        } else if (SocketMessage.MessageType.PACE.equals(message.getType())) {
            message.setMessage("매칭 진행중");

//            List<UserNowPace> paces = message.getPaces();
            Long matchId = message.getMatchId();
            Long userId = message.getUserId();
            Float nowDistance = message.getNowDistance();
            Integer nowTime = message.getNowTime();
            String newPace = message.getNowPace();

            message.setNowPaces(matchService.nowPaces(matchId, userId, nowDistance, nowTime, newPace));

            template.convertAndSend("/sub/usermatch/" + message.getMatchId(), message);
        }
    }
}
