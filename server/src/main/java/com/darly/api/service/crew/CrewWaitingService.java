package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewWaiting;

import java.util.Optional;

public interface CrewWaitingService {
    void deleteByCrewId(Long crewId);
    void createCrewWaiting(Long userId, Long crewId);
    void deleteByCrewIdAndUserId(Long crewId, Long userId);
    Optional<CrewWaiting> getCrewWaiting(Long userId, Long crewId);
    void deleteByCrewWaiting(CrewWaiting crewWaiting);
}
