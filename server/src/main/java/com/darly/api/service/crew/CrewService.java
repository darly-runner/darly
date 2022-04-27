package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.api.request.crew.CrewMandatePatchReq;
import com.darly.api.request.crew.CrewUpdatePatchReq;
import com.darly.api.request.crew.CrewUpdatePutReq;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewDetailMapping;
import com.darly.db.entity.crew.CrewTitleMapping;

import java.util.List;
import java.util.Optional;

public interface CrewService {
    Crew createCrew(Long userId, CrewCreatePostReq crewCreatePostReq);
    List<CrewTitleMapping> getCrewSearchListByAddressAndKey(Long userId, Integer address, String key, Integer limit, Integer offset);
    List<CrewTitleMapping> getCrewSearchListByKey(Long userId, String key, Integer limit, Integer offset);
    Long getCrewCountByAddressAndKey(Long userId, Integer address, String key);
    Long getCrewCountByKey(Long userId, String key);
    List<CrewDetailMapping> getCrewDetailByCrewId(Long crewId);
    Optional<Crew> getCrewByCrewId(Long crewId);
    void updateCrew(Crew crew, CrewUpdatePutReq crewUpdatePutReq);
    void updateCrewNotice(Crew crew, CrewUpdatePatchReq crewUpdatePatchReq);
    void updateCrewHost(Crew crew, CrewMandatePatchReq crewMandatePatchReq);
    void deleteCrew(Long crewId);
}
