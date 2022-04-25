package com.darly.db.repository.day;

import com.darly.db.entity.day.Day;
import com.darly.db.entity.day.QDay;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DayRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QDay qDay = QDay.day;

    public List<Day> findByStartAndEnd(Long userId, Long startDay, Long endDay) {
        return jpaQueryFactory.selectFrom(qDay)
                .where(qDay.userId.eq(userId), qDay.dayDate.goe(startDay), qDay.dayDate.lt(endDay))
                .fetch();
    }
}
