package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.response.match.MatchInRes;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.match.Match;
import com.darly.db.entity.match.MatchRUser;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service("matchService")
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final UserMatchRepository userMatchRepository;
    private final UserMatchRepositorySupport userMatchRepositorySupport;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private PriorityQueue<MatchRUser> userQueue = new PriorityQueue();

    @Override
    public void setNullByCrewId(Long crewId) {
        matchRepository.setNullByCrewId(crewId);
    }

    @Override
    public Page<Match> getCrewMatchList(Long crewId, Pageable page) {

        return matchRepository.findByCrew_CrewIdAndMatchStatusIsNot(crewId, 'U', page);
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

        return getMatchRefresh(matchId, userId);
    }

    // 현재 유저의 정보만 가져오기
    @Override
    public MatchInRes getMatchRefresh(Long matchId, Long userId) {
        Match match = matchRepository.findByMatchId(matchId);
        User enterUser = userRepository.findById(userId).get();

        List<UserMatch> userMatch = userMatchRepository.findAllByUserMatchId_Match_MatchId(matchId);
        List<UserMatchMapping> userMatches = new ArrayList();

        Integer imHost;
        if(match.getHost().getUserNickname().equals(enterUser.getUserNickname())){
            imHost = 1;
        }
        else {
            imHost = 0;
        }

        for(UserMatch user : userMatch) {
                Integer isHost;
                if (match.getHost().getUserNickname()
                        .equals(user.getUserMatchId().getUser().getUserNickname())) {
                    isHost = 1;
                } else {
                    isHost = 0;
                }

                Float userTotalPace = user.getUserMatchId().getUser().getUserTotalPace();
                Long recordNum = recordRepository.countAllByUser_UserId(user.getUserMatchId().getUser().getUserId());
                Float userPaceAvg;
                if (recordNum == 0) {
                    userPaceAvg = 0f;
                } else {
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
                .imHost(imHost)
                .matchStatus(match.getMatchStatus())
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
        System.out.println(hostId);
        System.out.println(userId);

        // 퇴장한 사람이 방장이 아님
        if(hostId != userId) {
            Short curPerson = match.getMatchCurPerson();
            curPerson--;
            match.setMatchCurPerson(curPerson);

            // 만약 현재 인원이 0명이 돼버리면
            if(curPerson == 0 ) {
                // 비활성화 상태로 변환
                match.setMatchStatus('E');
            }
            matchRepository.save(match);
            // 나간사람 1명만 삭제
            userMatchRepository.delete(userMatch);
        }
        // 퇴장한 사람이 방장, 아직 시작안한방에서 방장나가면 방 비활성화 상태로 바꾸고 전부 내보냄
        else if (hostId == userId && match.getMatchStatus() == 'W'){
            Short curPerson = 0;
            match.setMatchCurPerson(curPerson);
            match.setMatchStatus('U');
            matchRepository.save(match);
            // userMatch 테이블의 matchId로 검색해 전부 삭제, match는 남아있지만 방에는 아무도 없음
            userMatchRepository.deleteAllByUserMatchId_Match_MatchId(matchId);
        }
        else if (hostId == userId && match.getMatchStatus() == 'S') {
            Short curPerson = match.getMatchCurPerson();
            curPerson--;
            match.setMatchCurPerson(curPerson);
            match.setMatchStatus('E');
            matchRepository.save(match);
            userMatchRepository.delete(userMatch);
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
    public void userReady(Long matchId, Long userId, Character isReady) {
        // matchid, userid로 특정 유저 찾아내기
        UserMatch userMatch = userMatchRepository.findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(matchId, userId);

        if(isReady.equals('R')) {
            userMatch.setUserMatchStatus('R');
        }
        else if(isReady.equals('N')) {
            userMatch.setUserMatchStatus('N');
        }
        userMatchRepository.save(userMatch);
    }

    @Override
    public void matchStart(Long matchId) {
        Match match = matchRepository.findByMatchId(matchId);

        // 시작상태로 매치 상태 바꿈
        match.setMatchStatus('S');
        matchRepository.save(match);
    }

    @Override
    public List<MatchRUser> randomMatch(Long userId) {
        // 지금 매칭 신청한 유저 찾기
        User user = userRepository.getById(userId);
        Long totalRecordNum = recordRepository.countAllByUser_UserId(userId);


        if (userQueue.size() == 0) {
            userQueue.add(MatchRUser.builder()
                            .user(user)
                            .totalRecordNum(totalRecordNum)
                    .build());
            return null;
        }
        else {
            List<MatchRUser> matchRUsers = new ArrayList<>();
            MatchRUser prev_ruser = userQueue.poll();

            Long prev_userId = prev_ruser.getUserId();
            matchRUsers.add(prev_ruser);

            User prev_user = userRepository.getById(prev_userId);

            matchRUsers.add(MatchRUser.builder()
                    .user(user)
                    .totalRecordNum(totalRecordNum)
                    .build());

            // 방 만들어주기 현재인원2 최대인원2 방상태는 바로 'S' 목표거리는 5km
            makeRandomMatch(user);

            // 방금 생성한 방의 matchId(방장이 user이고 crewId는 null, 방상태는 'S')
            makeUserMatch(user, prev_user);

            return matchRUsers;
        }
    }

    private void makeRandomMatch(User user) {
        Match match = Match.builder()
                .crew(null)
                .host(user)
                .matchMaxPerson((short)2)
                .matchCurPerson((short)2)
                .matchStatus('S')
                .matchTitle("경쟁전")
                .matchDate(getTimestamp())
                .matchStartTime(getTimestamp())
                .matchGoalDistance(5f)
                .build();
        matchRepository.save(match);
    }

    private void makeUserMatch(User user, User prev_user) {
        Match match = matchRepository.findByHostAndCrewAndMatchStatus(user, null, 'S');

        UserMatchId userMatchId = UserMatchId.builder()
                .user(user)
                .match(match)
                .build();

        UserMatchId prev_userMatchId = UserMatchId.builder()
                .user(prev_user)
                .match(match)
                .build();

        UserMatch userMatch = UserMatch.builder()
                .userMatchId(userMatchId)
                .userMatchStatus('R')
                .build();

        UserMatch prev_userMatch = UserMatch.builder()
                .userMatchId(prev_userMatchId)
                .userMatchStatus('R')
                .build();

        userMatchRepository.save(userMatch);
        userMatchRepository.save(prev_userMatch);
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now()).getTime() / 1000;
    }
}
