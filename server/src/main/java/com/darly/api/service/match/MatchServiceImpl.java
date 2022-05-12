package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.response.match.MatchInRes;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.match.UserMatchId;
import com.darly.db.entity.user.User;
import com.darly.db.entity.user.UserMatchMapping;
import com.darly.db.repository.match.MatchRepository;
import com.darly.db.repository.match.UserMatchRepository;
import com.darly.db.repository.match.UserMatchRepositorySupport;
import com.darly.db.repository.record.RecordRepository;
import com.darly.db.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("matchService")
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final UserMatchRepository userMatchRepository;
    private final UserMatchRepositorySupport userMatchRepositorySupport;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    @Override
    public void setNullByCrewId(Long crewId) {
        matchRepository.setNullByCrewId(crewId);
    }

    @Override
    public Page<Match> getCrewMatchList(Long crewId, Pageable page) {
        return matchRepository.findByCrew_CrewId(crewId, page);
    }

    @Override
    public Match createCrewMatch(Long crewId, Long userId, MatchCreatePostReq matchCreatePostReq) {
        return matchRepository.save(Match.builder()
                .crew(Crew.builder().crewId(crewId).build())
                .host(User.builder().userId(userId).build())
                .matchTitle(matchCreatePostReq.getMatchTitle())
                .matchMaxPerson(matchCreatePostReq.getMatchMaxPerson())
                .matchGoalDistance(matchCreatePostReq.getMatchGoalDistance())
                .matchDate(getTimestamp())
                .matchStatus('W')
                .build());
    }

    // 방 참가. UserMatch 테이블에 유저 정보 추가, curperson++, 방정보 보내주기
    @Override
    public MatchInRes getMatchInfo(Long matchId, Long userId) {

        Match match = matchRepository.findByMatchId(matchId);
        User enterUser = userRepository.findById(userId).get();

        UserMatchId userMatchId = UserMatchId.builder()
                .match(match)
                .user(enterUser)
                .build();

        UserMatch enterUserMatch = UserMatch.builder()
                .userMatchId(userMatchId)
                .userMatchStatus('N')
                .build();

        userMatchRepository.save(enterUserMatch);


        Short curPerson = match.getMatchCurPerson();
        curPerson++;
        match.setMatchCurPerson(curPerson);
        matchRepository.save(match);

        List<UserMatch> userMatch = userMatchRepository.findAllByUserMatchId_Match_MatchId(matchId);
//        Collections.reverse(userMatch);
        List<UserMatchMapping> userMatches = new ArrayList();

        for(UserMatch user : userMatch) {
            Integer isHost;
            if(match.getHost().getUserNickname()
                    .equals(user.getUserMatchId().getUser().getUserNickname())){
                isHost = 1;
            }
            else {
                isHost = 0;
            }

            Float userTotalPace = user.getUserMatchId().getUser().getUserTotalPace();
            Long recordNum = recordRepository.countAllByUser_UserId(user.getUserMatchId().getUser().getUserId());
            Float userPaceAvg;
            if(recordNum == 0){
                userPaceAvg = 0f;
            }
            else {
                userPaceAvg = userTotalPace / recordNum;
            }

            userMatches.add(UserMatchMapping.builder()
                    .userId(user.getUserMatchId().getUser().getUserId())
                    .userNickname(user.getUserMatchId().getUser().getUserNickname())
                    .userImage(user.getUserMatchId().getUser().getUserImage())
                    .userTotalDistance(user.getUserMatchId().getUser().getUserTotalDistance())
                    .userPaceAvg(userPaceAvg)
                    .userStatus(user.getUserMatchStatus())
                    .isHost(isHost)
                    .build());
        }


        return MatchInRes.builder()
                .statusCode(200)
                .message("success")
                .myUserId(userId)
                .match(match)
                .userMatches(userMatches)
                .build();
    }

    // 방 퇴장, usermatch에서 해당 user 삭제, curperson--
    @Override
    public void matchOut(Long matchId, Long userId) {
        UserMatch userMatch = userMatchRepository.findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(matchId, userId);
        Match match = matchRepository.findByMatchId(matchId);
        Long hostId = match.getHost().getUserId();

        // 퇴장한 사람이 방장이 아님
        if(!Objects.equals(hostId, userId)) {
            Short curPerson = match.getMatchCurPerson();
            curPerson--;
            match.setMatchCurPerson(curPerson);

            // 만약 현재 인원이 0명이 돼버리면
            if(curPerson == 0) {
                // 비활성화 상태로 변환
                match.setMatchStatus('U');
            }
            matchRepository.save(match);
            // 나간사람 1명만 삭제
            userMatchRepository.delete(userMatch);
        }
        // 퇴장한 사람이 방장, 아직 시작안한방에서 방장나가면 방 비활성화 상태로 바꾸고 전부 내보냄
        else if (Objects.equals(hostId, userId) && match.getMatchStatus().equals('W')){
            Short curPerson = 0;
            match.setMatchCurPerson(curPerson);
            match.setMatchStatus('U');
            matchRepository.save(match);
            // userMatch 테이블의 matchId로 검색해 전부 삭제, match는 남아있지만 방에는 아무도 없음
            userMatchRepository.deleteAllByUserMatchId_Match_MatchId(matchId);
        }
    }

    // 방 정보 수정, matchTitle, matchMaxPerson, matchGoalDistance를 수정
    @Override
    public void patchMatchInfo(Long matchId, MatchPatchReq matchPatchReq) {
        Match match = matchRepository.findByMatchId(matchId);

        match.setMatchTitle(matchPatchReq.getMatchTitle());
        match.setMatchMaxPerson(matchPatchReq.getMatchMaxPerson());
        match.setMatchGoalDistance(matchPatchReq.getMatchGoalDistance());

        matchRepository.save(match);
    }

    @Override
    public void userReady(Long matchId, Long userId) {
        // matchid, userid로 특정 유저 찾아내기
        UserMatch userMatch = userMatchRepository.findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(matchId, userId);

        userMatch.setUserMatchStatus('R');
        userMatchRepository.save(userMatch);
    }

    @Override
    public void userUnReady(Long matchId, Long userId) {
        UserMatch userMatch = userMatchRepository.findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(matchId, userId);

        userMatch.setUserMatchStatus('N');
        userMatchRepository.save(userMatch);
    }

    @Override
    public void matchStart(Long matchId) {
        Match match = matchRepository.findByMatchId(matchId);

        // 시작상태로 매치 상태 바꿈
        match.setMatchStatus('S');
        matchRepository.save(match);
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

}
