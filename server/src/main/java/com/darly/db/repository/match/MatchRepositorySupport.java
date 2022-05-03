package com.darly.db.repository.match;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

//    QMatch qMatch = QMatch.match;
//
//    public List<MatchTitleMapping> findByCrewId(Long crewId) {
//        return jpaQueryFactory.select(new QMatchTitleMapping(qMatch.host.userNickname, qMatch.host.userImage, qMatch.matchId, qMatch.matchTitle, qMatch.matchMaxPerson, qMatch.matchCurPerson, qMatch.matchGoalDistance, qMatch.matchDate, qMatch.matchStartTime, qMatch.matchStatus))
//                .from(qMatch)
//                .where(qMatch.crew.crewId.eq(crewId))
//                .fetch();
//    }
}
