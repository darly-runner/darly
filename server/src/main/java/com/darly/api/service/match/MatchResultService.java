package com.darly.api.service.match;

import com.darly.db.entity.match.MatchResultMapping;

import java.util.List;

public interface MatchResultService {
    List<MatchResultMapping> getMatchResultList(Long matchId);
}
