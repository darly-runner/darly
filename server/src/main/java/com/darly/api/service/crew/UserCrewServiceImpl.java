package com.darly.api.service.crew;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewMyMapping;
import com.darly.db.entity.crew.UserCrew;
import com.darly.db.entity.crew.UserCrewId;
import com.darly.db.entity.user.UserTitleMapping;
import com.darly.db.repository.crew.UserCrewRepository;
import com.darly.db.repository.crew.UserCrewRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userCrewService")
@RequiredArgsConstructor
public class UserCrewServiceImpl implements UserCrewService {
    private final UserCrewRepository userCrewRepository;
    private final UserCrewRepositorySupport userCrewRepositorySupport;

    @Override

    public boolean createUserCrew(Long userId, Long crewId) {
        return userCrewRepository.save(new UserCrew(UserCrewId.builder().userId(userId).crewId(crewId).build())) == null ? false : true;
    }

    @Override
    public List<CrewMyMapping> getMyCrewList(Long userId) {
        return userCrewRepositorySupport.findByUserId(userId);
    }

    @Override
    public void leaveCrew(Long crewId, Long userId) {
        userCrewRepository.delete(new UserCrew(UserCrewId.builder().userId(userId).crewId(crewId).build()));
    }

    @Override
    public Long countUserNum(Long crewId) {
        return userCrewRepository.countByUserCrewId_Crew(Crew.builder().crewId(crewId).build());
    }

    @Override
    public boolean isUserCrewExists(Long userId, Long crewId) {
        return userCrewRepository.existsByUserCrewId(UserCrewId.builder().userId(userId).crewId(crewId).build());
    }

    @Override
    public List<UserTitleMapping> getCrewPeopleList(Long crewId) {
        return userCrewRepositorySupport.findTitleMappingByCrewId(crewId);
    }
}
