package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewMyMapping;
import com.darly.db.entity.crew.UserCrew;
import com.darly.db.entity.crew.UserCrewId;
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
}
