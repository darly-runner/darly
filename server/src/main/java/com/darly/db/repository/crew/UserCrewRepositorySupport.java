package com.darly.db.repository.crew;

import com.darly.db.entity.crew.CrewMyMapping;
import com.darly.db.entity.crew.QCrew;
import com.darly.db.entity.crew.QCrewMyMapping;
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
public class UserCrewRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QUserCrew qUserCrew = QUserCrew.userCrew;
    QCrew qCrew = QCrew.crew;
    QUser qUser = QUser.user;

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
}
