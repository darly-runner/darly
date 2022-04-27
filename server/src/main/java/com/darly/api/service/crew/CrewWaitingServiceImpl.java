package com.darly.api.service.crew;

import com.darly.db.entity.crew.Crew;
import com.darly.db.repository.crew.CrewWaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("crewWaitingService")
@RequiredArgsConstructor
public class CrewWaitingServiceImpl implements CrewWaitingService {
    private final CrewWaitingRepository crewWaitingRepository;

    @Override
    public void deleteByCrewId(Long crewId) {
        crewWaitingRepository.deleteByCrewWaitingId_Crew(Crew.builder().crewId(crewId).build());
    }
}
