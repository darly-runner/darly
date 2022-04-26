package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.api.service.file.FileProcessService;
import com.darly.common.util.Type;
import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewDetailMapping;
import com.darly.db.entity.crew.CrewTitleMapping;
import com.darly.db.entity.user.User;
import com.darly.db.repository.crew.CrewRepository;
import com.darly.db.repository.crew.CrewRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("crewService")
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {
    private final CrewRepository crewRepository;
    private final CrewRepositorySupport crewRepositorySupport;
    private final FileProcessService fileProcessService;

    @Override
    public Crew createCrew(Long userId, CrewCreatePostReq crewCreatePostReq) {
        String url = null;
        if (crewCreatePostReq.getCrewImage() != null)
            url = fileProcessService.uploadImage(crewCreatePostReq.getCrewImage(), "crew");

        return crewRepository.save(Crew.builder()
                .user(User.builder().userId(userId).build())
                .crewName(crewCreatePostReq.getCrewName())
                .crewDesc(crewCreatePostReq.getCrewDesc())
                .crewJoin(Type.valueOf(crewCreatePostReq.getCrewJoin()).getLabel())
                .crewImage(url)
                .build());
    }

    @Override
    public List<CrewTitleMapping> getCrewSearchListByAddressAndKey(Long userId, Integer address, String key, Integer limit, Integer offset) {
        return crewRepository.findByUserIdAndAddressAndKey(userId, address, key, limit, offset);
    }

    @Override
    public List<CrewTitleMapping> getCrewSearchListByKey(Long userId, String key, Integer limit, Integer offset) {
        return crewRepository.findByUserIdAndKey(userId, key, limit, offset);
    }

    @Override
    public Long getCrewCountByAddressAndKey(Long userId, Integer address, String key) {
        return crewRepository.countByUserIdAndAddressAndKey(userId, address, key);
    }

    @Override
    public Long getCrewCountByKey(Long userId, String key) {
        return crewRepository.countByUserIdAndKey(userId, key);
    }

    @Override
    public CrewDetailMapping getCrewDetailByCrewId(Long crewId) {
        return crewRepositorySupport.findCrewDetailByCrewId(crewId);
    }
}
