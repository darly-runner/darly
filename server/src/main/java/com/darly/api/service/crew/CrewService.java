package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.db.entity.crew.Crew;

import java.util.Optional;

public interface CrewService {
    Crew createCrew(Long userId, CrewCreatePostReq crewCreatePostReq);
}
