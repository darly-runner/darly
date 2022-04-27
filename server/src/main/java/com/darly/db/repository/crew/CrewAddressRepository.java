package com.darly.db.repository.crew;

import com.darly.db.entity.crew.CrewAddress;
import com.darly.db.entity.crew.CrewAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewAddressRepository extends JpaRepository<CrewAddress, CrewAddressId> {
}
