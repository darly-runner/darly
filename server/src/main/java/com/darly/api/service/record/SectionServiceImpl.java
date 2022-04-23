package com.darly.api.service.record;

import com.darly.api.request.record.SectionReq;
import com.darly.db.entity.record.Section;
import com.darly.db.repository.record.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sectionService")
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;

    @Override
    public void createSection(Long recordId, List<SectionReq> sectionList) {
        for (SectionReq s : sectionList) {
            sectionRepository.save(Section.builder()
                    .recordId(recordId)
                    .sectionKm(s.getKm())
                    .sectionPace(s.getPace())
                    .sectionCalories(s.getCalories())
                    .build());
        }
    }
}
