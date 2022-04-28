package com.darly.api.service.match;

import com.darly.db.entity.match.MatchTitleMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchService {
    void setNullByCrewId(Long crewId);
    Page<MatchTitleMapping> getCrewMatchList(Long crewId, Pageable page);
}
