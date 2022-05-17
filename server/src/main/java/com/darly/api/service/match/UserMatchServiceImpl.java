package com.darly.api.service.match;

import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.match.UserMatchId;
import com.darly.db.entity.user.User;
import com.darly.db.repository.match.MatchRepository;
import com.darly.db.repository.match.UserMatchRepository;
import com.darly.db.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userMatchService")
@RequiredArgsConstructor
public class UserMatchServiceImpl implements UserMatchService {
    private final UserMatchRepository userMatchRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    @Override
    public void createUserMatch(Long userId, Long matchId) {
        userMatchRepository.save(UserMatch.builder()
                .userMatchId(UserMatchId.builder()
                        .user(userRepository.findById(userId).get())
                        .match(matchRepository.findByMatchId(matchId))
                        .build())
                .userMatchStatus('N')
                .build());
    }

    @Override
    public List<UserMatch> findUsersByMatchId(Long matchId) {
        return userMatchRepository.findAllByUserMatchId_Match_MatchId(matchId);
    }
}
