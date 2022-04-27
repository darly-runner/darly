package com.darly.db.repository.crew;

import com.darly.db.entity.crew.CrewMyMapping;
import com.darly.db.entity.crew.QCrew;
import com.darly.db.entity.crew.QCrewMyMapping;
import com.darly.db.entity.crew.QUserCrew;
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

    public List<CrewMyMapping> findByUserId(Long userId) {
        return jpaQueryFactory.select(new QCrewMyMapping(qCrew.crewId, qCrew.crewName, qCrew.crewImage))
                .from(qCrew).innerJoin(qUserCrew)
                .on(qUserCrew.userCrewId.crew.crewId.eq(qCrew.crewId))
                .where(qUserCrew.userCrewId.user.userId.eq(userId))
                .fetch();
    }
}
