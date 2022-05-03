package com.darly.db.repository.crew;

import com.darly.db.entity.crew.*;
import com.darly.db.entity.day.QDay;
import com.darly.db.entity.user.QUser;
import com.darly.db.entity.user.QUserTitleMapping;
import com.darly.db.entity.user.UserTitleMapping;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCrewRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QUserCrew qUserCrew = QUserCrew.userCrew;
    QCrew qCrew = QCrew.crew;
    QUser qUser = QUser.user;
    QDay qDay = QDay.day;

    public List<CrewMyMapping> findByUserId(Long userId) {
        return jpaQueryFactory.select(new QCrewMyMapping(qCrew.crewId, qCrew.crewName, qCrew.crewImage))
                .from(qCrew).innerJoin(qUserCrew)
                .on(qUserCrew.userCrewId.crew.crewId.eq(qCrew.crewId))
                .where(qUserCrew.userCrewId.user.userId.eq(userId))
                .fetch();
    }

    public List<UserTitleMapping> findTitleMappingByCrewId(Long crewId) {
        return jpaQueryFactory.select(new QUserTitleMapping(qUser.userId, qUser.userNickname, qUser.userMessage, qUser.userImage))
                .from(qUserCrew)
                .innerJoin(qUser)
                .on(qUserCrew.userCrewId.user.userId.eq(qUser.userId))
                .where(qUserCrew.userCrewId.crew.crewId.eq(crewId))
                .fetch();
    }

    public List<CrewSummaryMapping> findCrewSummaryByCrewId(Long crewId) {
        return jpaQueryFactory.select(new QCrewSummaryMapping(qUser.userNickname, qUser.userImage, qDay.dayDistance.sum(), qDay.dayTime.sum(), qDay.dayNum.sum(), qDay.dayPace.sum()))
                .from(qUserCrew)
                .innerJoin(qUser)
                .on(qUserCrew.userCrewId.user.userId.eq(qUser.userId))
                .innerJoin(qDay)
                .on(qUserCrew.userCrewId.user.userId.eq(qDay.user.userId))
                .where(qUserCrew.userCrewId.crew.crewId.eq(crewId))
                .groupBy(qUser.userId)
                .fetch();
    }

    public List<CrewSummaryMapping> findCrewSummaryByCrewIdAndDate(Long crewId, long startDay, long endDay) {
        return jpaQueryFactory.select(new QCrewSummaryMapping(qUser.userNickname, qUser.userImage, qDay.dayDistance.sum(), qDay.dayTime.sum(), qDay.dayNum.sum(), qDay.dayPace.sum()))
                .from(qUserCrew)
                .innerJoin(qUser)
                .on(qUserCrew.userCrewId.user.userId.eq(qUser.userId))
                .innerJoin(qDay)
                .on(qUserCrew.userCrewId.user.userId.eq(qDay.user.userId))
                .where(qUserCrew.userCrewId.crew.crewId.eq(crewId), qDay.dayDate.goe(startDay), qDay.dayDate.lt(endDay))
                .groupBy(qUser.userId)
                .fetch();
    }
}
