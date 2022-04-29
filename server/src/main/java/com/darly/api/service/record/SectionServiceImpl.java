package com.darly.api.service.record;

import com.darly.api.request.record.SectionReq;
import com.darly.db.entity.record.Record;
import com.darly.db.entity.record.Section;
import com.darly.db.entity.record.SectionMapping;
import com.darly.db.repository.record.SectionRepository;
import com.darly.db.repository.record.SectionRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sectionService")
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final SectionRepositorySupport sectionRepositorySupport;

    @Override
    public void createSection(Long recordId, List<SectionReq> sectionList) {
        for (SectionReq s : sectionList) {
            sectionRepository.save(Section.builder()
                    .record(Record.builder().recordId(recordId).build())
                    .sectionKm(s.getKm())
                    .sectionPace(s.getPace())
                    .sectionCalories(s.getCalories())
                    .build());
        }
    }

    @Override
    public List<SectionMapping> getSectionList(Long recordId) {
        return sectionRepositorySupport.getSectionList(recordId);
    }
}
