package com.darly.db.repository.crew;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewAddress;
import com.darly.db.entity.crew.CrewAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrewAddressRepository extends JpaRepository<CrewAddress, CrewAddressId> {
    Optional<CrewAddress> findByCrewAddressId_CrewId(Long crewId);
}
