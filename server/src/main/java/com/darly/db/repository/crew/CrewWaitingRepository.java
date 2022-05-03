package com.darly.db.repository.crew;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewWaiting;
import com.darly.db.entity.crew.CrewWaitingId;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CrewWaitingRepository extends JpaRepository<CrewWaiting, CrewWaitingId> {
    @Transactional
    void deleteByCrewWaitingId_Crew(Crew crew);

    @Transactional
    void deleteByCrewWaitingId(CrewWaitingId crewWaitingId);
}
