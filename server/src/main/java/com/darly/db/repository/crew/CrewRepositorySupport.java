package com.darly.db.repository.crew;

import com.darly.db.entity.crew.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CrewRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QCrew qCrew = QCrew.crew;
    QCrewAddress qCrewAddress = QCrewAddress.crewAddress;
    QUserCrew qUserCrew = QUserCrew.userCrew;
    QCrewFeed qCrewFeed = QCrewFeed.crewfeed;


    public CrewDetailMapping findCrewDetailByCrewId(Long crewId) {
        return jpaQueryFactory.select(new QCrewDetailMapping(qCrew.crewName, qCrew.crewDesc, qCrew.crewNotice, qCrew.user.userNickname, qCrewAddress.crewAddressId.address.addressName, qCrew.crewImage,
                ExpressionUtils.as(
                        JPAExpressions.select(qUserCrew.userCrewId.user.userId.count())
                                .from(qUserCrew)
                                .where(qUserCrew.userCrewId.crew.crewId.eq(qCrew.crewId)),
                        "crewPeople"), ExpressionUtils.as(
                JPAExpressions.select(qCrewFeed.crewFeedId.feed.feedId.count())
                        .from(qCrewFeed)
                        .where(qCrewFeed.crewFeedId.crew.crewId.eq(qCrew.crewId)),
                "crewFeedNum"))
                .from(qCrew).innerJoin(qCrewAddress)
                .on(qCrew.crewId.eq(qCrewAddress.crewAddressId.crewId))
                .where(qCrew.crewId.eq(crewId))
                .fetch().get(0);
    }
}
