package com.darly.db.repository.record;

import com.darly.db.entity.record.QRecordMapping;
import com.darly.db.entity.record.QSection;
import com.darly.db.entity.record.QSectionMapping;
import com.darly.db.entity.record.SectionMapping;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SectionRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QSection qSection = QSection.section;

    public List<SectionMapping> getSectionList(Long recordId) {
        return jpaQueryFactory.select(new QSectionMapping(qSection.sectionKm, qSection.sectionPace, qSection.sectionCalories))
                .from(qSection)
                .where(qSection.recordId.eq(recordId))
                .fetch();
    }
}
