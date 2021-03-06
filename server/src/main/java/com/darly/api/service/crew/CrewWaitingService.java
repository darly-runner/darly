package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewWaiting;
import com.darly.db.entity.user.UserTitleMapping;

import java.util.List;
import java.util.Optional;

public interface CrewWaitingService {
    void deleteByCrewId(Long crewId);
    void createCrewWaiting(Long userId, Long crewId);
    void deleteByUserIdAndCrewId(Long userId, Long crewId);
    Optional<CrewWaiting> getCrewWaiting(Long userId, Long crewId);
    void deleteByCrewWaiting(CrewWaiting crewWaiting);
    List<UserTitleMapping> getCrewWaitingList(Long crewId);
    boolean isCrewWaitingExists(Long userId, Long crewId);
}
