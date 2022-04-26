package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewTitleMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface CrewService {
    Crew createCrew(Long userId, CrewCreatePostReq crewCreatePostReq);
    Page<CrewTitleMapping> getCrewSearchListByAddressAndKey(Long userId, Integer address, String key, Pageable pageRequest);
    Page<CrewTitleMapping> getCrewSearchListByKey(Long userId, String key, Pageable pageRequest);
}
