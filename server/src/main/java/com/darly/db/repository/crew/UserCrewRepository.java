package com.darly.db.repository.crew;

import com.darly.db.entity.crew.UserCrew;
import com.darly.db.entity.crew.UserCrewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCrewRepository extends JpaRepository<UserCrew, UserCrewId> {
}
