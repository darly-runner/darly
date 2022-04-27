package com.darly.api.service.crew;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewWaiting;
import com.darly.db.entity.crew.CrewWaitingId;
import com.darly.db.repository.crew.CrewWaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("crewWaitingService")
@RequiredArgsConstructor
public class CrewWaitingServiceImpl implements CrewWaitingService {
    private final CrewWaitingRepository crewWaitingRepository;

    @Override
    public void deleteByCrewId(Long crewId) {
        crewWaitingRepository.deleteByCrewWaitingId_Crew(Crew.builder().crewId(crewId).build());
    }

    @Override
    public void createCrewWaiting(Long userId, Long crewId) {
        crewWaitingRepository.save(CrewWaiting.builder()
                .crewWaitingId(
                        CrewWaitingId.builder()
                                .crewId(crewId)
                                .userId(userId)
                                .build())
                .build());
    }

    @Override
    public void deleteByUserIdAndCrewId(Long userId, Long crewId) {
        crewWaitingRepository.delete(CrewWaiting.builder().crewWaitingId(CrewWaitingId.builder().crewId(crewId).userId(userId).build()).build());
    }

    @Override
    public Optional<CrewWaiting> getCrewWaiting(Long userId, Long crewId) {
        return crewWaitingRepository.findById(CrewWaitingId.builder().userId(userId).crewId(crewId).build());
    }

    @Override
    public void deleteByCrewWaiting(CrewWaiting crewWaiting) {
        crewWaitingRepository.delete(crewWaiting);
    }
}
