package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewDetailMapping;
import com.darly.db.entity.crew.CrewTitleMapping;

import java.util.List;

public interface CrewService {
    Crew createCrew(Long userId, CrewCreatePostReq crewCreatePostReq);
    List<CrewTitleMapping> getCrewSearchListByAddressAndKey(Long userId, Integer address, String key, Integer limit, Integer offset);
    List<CrewTitleMapping> getCrewSearchListByKey(Long userId, String key, Integer limit, Integer offset);
    Long getCrewCountByAddressAndKey(Long userId, Integer address, String key);
    Long getCrewCountByKey(Long userId, String key);
    List<CrewDetailMapping> getCrewDetailByCrewId(Long crewId);
}
