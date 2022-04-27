package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewMyMapping;

import java.util.List;

public interface UserCrewService {
    boolean createUserCrew(Long userId, Long crewId);
    List<CrewMyMapping> getMyCrewList(Long userId);
    void leaveCrew(Long crewId, Long userId);
}
