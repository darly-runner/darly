package com.darly.db.repository.crew;

import com.darly.db.entity.crew.CrewMyMapping;
import com.darly.db.entity.crew.UserCrew;
import com.darly.db.entity.crew.UserCrewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCrewRepository extends JpaRepository<UserCrew, UserCrewId> {
}
