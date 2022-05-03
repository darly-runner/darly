package com.darly.db.repository.record;

import com.darly.db.entity.record.QCoordinate;
import com.darly.db.entity.record.QRecord;
import com.darly.db.entity.record.QRecordMapping;
import com.darly.db.entity.record.RecordMapping;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QRecord qRecord = QRecord.record;
    QCoordinate qCoordinate = QCoordinate.coordinate;

    public List<RecordMapping> getRecordListAll(Long userId) {
        return jpaQueryFactory.select(new QRecordMapping(qRecord.recordId, qRecord.recordTitle, qRecord.recordDate, qRecord.recordDistance, qRecord.recordTime, qRecord.recordPace, qRecord.recordHeart, qRecord.recordCalories, qCoordinate.coordinateLatitude, qCoordinate.coordinateLongitude))
                .from(qRecord).innerJoin(qCoordinate)
                .on(qRecord.recordId.eq(qCoordinate.record.recordId))
                .where(qRecord.user.userId.eq(userId))
                .orderBy(qRecord.recordId.desc())
                .fetch();
    }

    public List<RecordMapping> getRecordListTop(Long userId) {
        return jpaQueryFactory.select(new QRecordMapping(qRecord.recordId, qRecord.recordTitle, qRecord.recordDate, qRecord.recordDistance, qRecord.recordTime, qRecord.recordPace, qRecord.recordHeart, qRecord.recordCalories, qCoordinate.coordinateLatitude, qCoordinate.coordinateLongitude))
                .from(qRecord).innerJoin(qCoordinate)
                .on(qRecord.recordId.eq(qCoordinate.record.recordId))
                .where(qRecord.user.userId.eq(userId))
                .limit(3)
                .orderBy(qRecord.recordId.desc())
                .fetch();
    }
}
