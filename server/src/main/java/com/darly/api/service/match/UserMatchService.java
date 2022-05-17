package com.darly.api.service.match;

import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.user.User;

import java.util.List;

public interface UserMatchService {
    void createUserMatch(Long userId, Long matchId);
    List<UserMatch> findUsersByMatchId(Long matchId);
}
