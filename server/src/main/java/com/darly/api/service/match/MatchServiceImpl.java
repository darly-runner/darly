package com.darly.api.service.match;

import com.darly.api.request.match.MatchCreatePostReq;
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
import org.springframework.transaction.annotation.Isolation;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public MatchInRes getMatchInfo(Long matchId, Long userId) {

        // 2. 현재 방의 정보 보내주기
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
                .match(match)
                .userMatches(userMatches)
                .build();
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

}
