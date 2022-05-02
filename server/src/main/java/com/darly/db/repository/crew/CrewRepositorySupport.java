package com.darly.db.repository.crew;

import com.darly.db.entity.crew.*;
import com.darly.db.entity.feed.QFeed;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrewRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QCrew qCrew = QCrew.crew;
    QFeed qFeed = QFeed.feed;
    QUserCrew qUserCrew = QUserCrew.userCrew;


    public List<CrewDetailMapping> findCrewDetailByCrewId(Long crewId) {
        return jpaQueryFactory.select(new QCrewDetailMapping(qCrew.crewName, qCrew.crewDesc, qCrew.crewNotice, qCrew.user.userNickname, qCrew.address.addressName, qCrew.crewImage,
                        ExpressionUtils.as(
                                JPAExpressions.select(qUserCrew.userCrewId.user.userId.count())
                                        .from(qUserCrew)
                                        .where(qUserCrew.userCrewId.crew.crewId.eq(crewId)),
                                "crewPeople"), ExpressionUtils.as(
                        JPAExpressions.select(qFeed.feedId.count())
                                .from(qFeed)
                                .where(qFeed.crew.crewId.eq(crewId)),
                        "crewFeedNum")))
                .from(qCrew)
                .where(qCrew.crewId.eq(crewId))
                .fetch();
    }
}
