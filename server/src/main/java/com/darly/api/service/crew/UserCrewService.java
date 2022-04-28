package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewMandatePatchReq;
import com.darly.db.entity.crew.CrewMyMapping;
import com.darly.db.entity.crew.CrewSummaryMapping;
import com.darly.db.entity.user.UserTitleMapping;

import java.util.List;

public interface UserCrewService {
    boolean createUserCrew(Long userId, Long crewId);
    List<CrewMyMapping> getMyCrewList(Long userId);
    void leaveCrew(Long crewId, Long userId);
    Long countUserNum(Long crewId);
    boolean isUserCrewExists(Long userId, Long crewId);
    List<UserTitleMapping> getCrewPeopleList(Long crewId);
    List<CrewSummaryMapping> getCrewSummaryList(Long crewId, String type);
}
