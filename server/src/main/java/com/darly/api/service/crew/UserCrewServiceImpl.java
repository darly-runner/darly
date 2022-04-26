package com.darly.api.service.crew;

import com.darly.db.entity.crew.UserCrew;
import com.darly.db.entity.crew.UserCrewId;
import com.darly.db.repository.crew.UserCrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("userCrewService")
@RequiredArgsConstructor
public class UserCrewServiceImpl implements UserCrewService {
    private final UserCrewRepository userCrewRepository;

    @Override
    public boolean createUserCrew(Long userId, Long crewId) {
        return userCrewRepository.save(new UserCrew(UserCrewId.builder().userId(userId).crewId(crewId).build())) == null ? false : true;
    }
}
