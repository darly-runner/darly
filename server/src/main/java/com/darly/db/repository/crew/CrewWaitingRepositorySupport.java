package com.darly.db.repository.crew;

import com.darly.db.entity.crew.QCrewWaiting;
import com.darly.db.entity.crew.QUserCrew;
import com.darly.db.entity.user.QUser;
import com.darly.db.entity.user.QUserTitleMapping;
import com.darly.db.entity.user.UserTitleMapping;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrewWaitingRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QUser qUser = QUser.user;
    QCrewWaiting qCrewWaiting = QCrewWaiting.crewWaiting;

    public List<UserTitleMapping> findTitleMappingByCrewId(Long crewId) {
        return jpaQueryFactory.select(new QUserTitleMapping(qUser.userId, qUser.userNickname, qUser.userMessage, qUser.userImage))
                .from(qCrewWaiting).innerJoin(qUser)
                .on(qCrewWaiting.crewWaitingId.user.userId.eq(qUser.userId))
                .where(qCrewWaiting.crewWaitingId.crew.crewId.eq(crewId))
                .fetch();
    }
}
