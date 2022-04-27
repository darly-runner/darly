package com.darly.api.service.crew;

public interface CrewWaitingService {
    void deleteByCrewId(Long crewId);
    void createCrewWaiting(Long userId, Long crewId);
}
