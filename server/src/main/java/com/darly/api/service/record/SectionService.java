package com.darly.api.service.record;

import com.darly.api.request.record.RecordCreatePostReq;
import com.darly.api.request.record.SectionReq;
import com.darly.db.entity.record.SectionMapping;

import java.util.List;

public interface SectionService {
    void createSection(Long recordId, List<SectionReq> sectionList);
    List<SectionMapping> getSectionList(Long recordId);
}
