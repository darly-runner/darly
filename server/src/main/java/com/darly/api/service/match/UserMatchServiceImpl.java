package com.darly.api.service.match;

import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.match.UserMatchId;
import com.darly.db.repository.match.UserMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userMatchService")
@RequiredArgsConstructor
public class UserMatchServiceImpl implements UserMatchService {
    private final UserMatchRepository userMatchRepository;

    @Override
    public void createUserMatch(Long userId, Long matchId) {
        userMatchRepository.save(UserMatch.builder()
                .userMatchId(UserMatchId.builder()
                        .userId(userId)
                        .matchId(matchId)
                        .build())
                .userMatchStatus('N')
                .build());
    }
}
