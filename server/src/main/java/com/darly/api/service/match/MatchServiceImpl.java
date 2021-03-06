package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
import com.darly.api.request.match.MatchPatchReq;
import com.darly.api.response.match.MatchInRes;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.match.*;
import com.darly.db.entity.user.User;
import com.darly.db.entity.user.UserMatchMapping;
import com.darly.db.entity.user.UserNowPace;
import com.darly.db.repository.match.MatchRepository;
import com.darly.db.repository.match.MatchResultRepository;
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
import java.util.*;

@Service("matchService")
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final UserMatchRepository userMatchRepository;
    private final UserMatchRepositorySupport userMatchRepositorySupport;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final MatchResultRepository matchResultRepository;
    private PriorityQueue<MatchRUser> userQueue = new PriorityQueue();

    private Map<Long, List<UserNowPace>> userPaceMap = new HashMap<>();
    private Map<Long, List<Long>> userResultMap = new HashMap<>();

    @Override
    public void setNullByCrewId(Long crewId) {
        matchRepository.setNullByCrewId(crewId);
    }

    @Override
    public Page<Match> getCrewMatchList(Long crewId, Pageable page) {

        return matchRepository.findByCrew_CrewIdAndMatchStatusIsNotAndMatchStatusIsNot(crewId, 'U', 'E', page);
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

    // ??? ??????. UserMatch ???????????? ?????? ?????? ??????, curperson++, ????????? ????????????
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

        match.setMatchCurPerson(userMatchRepository.countAllByUserMatchId_Match_MatchId(matchId));
        matchRepository.save(match);

        return getMatchRefresh(matchId, userId);
    }

    // ?????? ????????? ????????? ????????????
    @Override
    public MatchInRes getMatchRefresh(Long matchId, Long userId) {
        Match match = matchRepository.findByMatchId(matchId);
        User enterUser = userRepository.findById(userId).get();

        List<UserMatch> userMatch = userMatchRepository.findAllByUserMatchId_Match_MatchId(matchId);
        List<UserMatchMapping> userMatches = new ArrayList();

        Integer imHost;
        if (match.getHost().getUserNickname().equals(enterUser.getUserNickname())) {
            imHost = 1;
        } else {
            imHost = 0;
        }

        for (UserMatch user : userMatch) {
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


    // ??? ??????, usermatch?????? ?????? user ??????, curperson--
    @Override
    public Character matchOut(Long matchId, Long userId) {
        UserMatch userMatch = userMatchRepository.findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(matchId, userId);
        Match match = matchRepository.findByMatchId(matchId);
        Long hostId = match.getHost().getUserId();

        // ????????? ????????? ????????? ??????
        if (hostId != userId) {
            userMatchRepository.delete(userMatch);

            match.setMatchCurPerson(userMatchRepository.countAllByUserMatchId_Match_MatchId(matchId));
            matchRepository.save(match);

            // ?????? ?????? ????????? 0?????? ????????????
            if (match.getMatchCurPerson() == 0) {
                // ???????????? ????????? ??????
                match.setMatchStatus('E');
            }
            matchRepository.save(match);
            // ???????????? 1?????? ??????

        }
        // ????????? ????????? ??????, ?????? ????????????????????? ??????????????? ??? ???????????? ????????? ????????? ?????? ?????????
        else if (hostId == userId && match.getMatchStatus() == 'W') {
            Short curPerson = 0;
            match.setMatchCurPerson(curPerson);
            match.setMatchStatus('U');
            matchRepository.save(match);
            // userMatch ???????????? matchId??? ????????? ?????? ??????, match??? ??????????????? ????????? ????????? ??????
            userMatchRepository.deleteAllByUserMatchId_Match_MatchId(matchId);
        } else if (hostId == userId && match.getMatchStatus() == 'S') {
            userMatchRepository.delete(userMatch);

            match.setMatchCurPerson(userMatchRepository.countAllByUserMatchId_Match_MatchId(matchId));
            if (match.getMatchCurPerson() == 0) {
                match.setMatchStatus('E');
            }
            matchRepository.save(match);
        }

        userPaceMap.remove(matchId);

        return match.getMatchStatus();
    }

    // ??? ?????? ??????, matchTitle, matchMaxPerson, matchGoalDistance??? ??????
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
        // matchid, userid??? ?????? ?????? ????????????
        UserMatch userMatch = userMatchRepository.findByUserMatchId_Match_MatchIdAndUserMatchId_User_UserId(matchId, userId);

        if (isReady.equals('R')) {
            userMatch.setUserMatchStatus('R');
        } else if (isReady.equals('N')) {
            userMatch.setUserMatchStatus('N');
        }
        userMatchRepository.save(userMatch);
    }

    @Override
    public void matchStart(Long matchId) {
        Match match = matchRepository.findByMatchId(matchId);

        // ??????????????? ?????? ?????? ??????
        match.setMatchStatus('S');
        matchRepository.save(match);

        List<UserMatch> userMatchList = userMatchRepository.findAllByUserMatchId_Match_MatchId(matchId);
        List<UserNowPace> userNowPaceList = new ArrayList<>();
        for (UserMatch userMatch : userMatchList) {
            userNowPaceList.add(UserNowPace.builder()
                    .userId(userMatch.getUserMatchId().getUser().getUserId())
                    .userNickname(userMatch.getUserMatchId().getUser().getUserNickname())
                    .userImage(userMatch.getUserMatchId().getUser().getUserImage())
                    .nowPace("--")
                    .nowPaceInt(0)
                    .nowDistance(0f)
                    .nowTime(0)
                    .build());
        }
        userPaceMap.put(matchId, userNowPaceList);
        List<Long> userResultList = new ArrayList<>();
        userResultMap.put(matchId, userResultList);
    }

    @Override
    public List<MatchRUser> randomMatch(Long userId) {
        // ?????? ?????? ????????? ?????? ??????
        User user = userRepository.getById(userId);
        Long totalRecordNum = recordRepository.countAllByUser_UserId(userId);

        if (userQueue.size() == 0) {
            userQueue.add(MatchRUser.builder()
                    .user(user)
                    .totalRecordNum(totalRecordNum)
                    .build());
            return null;
        } else {
            List<MatchRUser> matchRUsers = new ArrayList<>();
            MatchRUser prev_ruser = userQueue.poll();

            Long prev_userId = prev_ruser.getUserId();
            matchRUsers.add(prev_ruser);

            User prev_user = userRepository.getById(prev_userId);

            matchRUsers.add(MatchRUser.builder()
                    .user(user)
                    .totalRecordNum(totalRecordNum)
                    .build());

            // ??? ??????????????? ????????????2 ????????????2 ???????????? ?????? 'S' ??????????????? 5km
            makeRandomMatch(user);

            // ?????? ????????? ?????? matchId(????????? user?????? crewId??? null, ???????????? 'S')
            makeUserMatch(user, prev_user);

            return matchRUsers;
        }
    }

    @Override
    public List<UserNowPace> nowUsers(Long matchId) {
        return userPaceMap.get(matchId);
    }

    @Override
    public List<UserNowPace> nowPaces(Long matchId, Long userId, Float nowDistance, Integer nowTime, String nowPace, Integer nowPaceInt) {
        List<UserNowPace> userList = userPaceMap.get(matchId);
        for (UserNowPace user : userList) {
            if (user.getUserId().equals(userId)) {
                user.setNowTime(nowTime);
                user.setNowDistance(nowDistance);
                user.setNowPace(nowPace);
                user.setNowPaceInt(nowPaceInt);
            }
        }
        Collections.sort(userList);
        userPaceMap.put(matchId, userList);
        return userList;
    }

    @Override
    public void resultMatch(Long matchId, Long userId, Integer nowTime, Integer nowPaceInt, Float nowDistance) {
        List<UserNowPace> userList = userPaceMap.get(matchId);
        for (UserNowPace user : userList) {
            System.out.println(user);
            if (user.getUserId().equals(userId)) {
                user.setNowTime(nowTime);
                if(nowDistance != null)
                    user.setNowDistance(nowDistance);
            }
        }
        Collections.sort(userList);
        for (int i = 0; i < userList.size(); i++) {
            UserNowPace userModel = userList.get(i);
            System.out.println(userModel);
            matchResultRepository.save(MatchResult.builder()
                    .matchId(matchId)
                    .user(User.builder().userId(userModel.getUserId()).build())
                    .matchResultPace(userModel.getNowPaceInt())
                    .matchResultRank((short) (i + 1))
                    .matchResultTime(userModel.getNowTime())
                    .build());
        }
        userPaceMap.remove(matchId);
    }

    private void makeRandomMatch(User user) {
        Match match = Match.builder()
                .crew(null)
                .host(user)
                .matchMaxPerson((short) 2)
                .matchCurPerson((short) 2)
                .matchStatus('S')
                .matchTitle("?????????")
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
