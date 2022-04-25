package com.darly.db.repository.match;

import com.darly.db.entity.match.MatchResultMapping;
import com.darly.db.entity.match.QMatchResult;
import com.darly.db.entity.match.QMatchResultMapping;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchResultRepositorySupport{
    private final JPAQueryFactory jpaQueryFactory;

    QMatchResult qMatchResult = QMatchResult.matchResult;

    public List<MatchResultMapping> getMatchResultList(Long matchId) {
        return jpaQueryFactory.select(new QMatchResultMapping(qMatchResult.user.userNickname, qMatchResult.user.userImage, qMatchResult.matchResultRank, qMatchResult.matchResultTime, qMatchResult.matchResultPace))
                .from(qMatchResult)
                .where(qMatchResult.matchId.eq(matchId))
                .fetch();
    }
}
