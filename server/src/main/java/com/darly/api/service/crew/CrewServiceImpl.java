package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.common.util.Type;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewTitleMapping;
import com.darly.db.entity.user.User;
import com.darly.db.repository.crew.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("crewService")
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {
    private final CrewRepository crewRepository;

    @Override
    public Crew createCrew(Long userId, CrewCreatePostReq crewCreatePostReq) {
        return crewRepository.save(Crew.builder()
                .user(User.builder().userId(userId).build())
                .crewName(crewCreatePostReq.getCrewName())
                .crewDesc(crewCreatePostReq.getCrewDesc())
                .crewJoin(Type.valueOf(crewCreatePostReq.getCrewJoin()).getLabel())
                .build());
    }

    @Override
    public Page<CrewTitleMapping> getCrewSearchList(Long userId, Integer address, String key, Pageable pageRequest) {
        return crewRepository.findByUserIdAndAddressAndKey(userId, address, key, pageRequest);
    }
}
