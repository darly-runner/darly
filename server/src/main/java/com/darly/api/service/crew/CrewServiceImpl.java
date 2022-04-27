package com.darly.api.service.crew;

import com.darly.api.request.crew.CrewCreatePostReq;
import com.darly.api.request.crew.CrewMandatePatchReq;
import com.darly.api.request.crew.CrewUpdatePatchReq;
import com.darly.api.request.crew.CrewUpdatePutReq;
import com.darly.api.service.address.AddressService;
import com.darly.api.service.feed.FeedService;
import com.darly.api.service.file.FileProcessService;
import com.darly.api.service.match.MatchService;
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
import java.util.Optional;

@Service("crewService")
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {
    private final CrewRepository crewRepository;
    private final CrewRepositorySupport crewRepositorySupport;
    private final CrewAddressService crewAddressService;
    private final CrewWaitingService crewWaitingService;
    private final FileProcessService fileProcessService;
    private final FeedService feedService;
    private final MatchService matchService;

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
    public List<CrewDetailMapping> getCrewDetailByCrewId(Long crewId) {
        return crewRepositorySupport.findCrewDetailByCrewId(crewId);
    }

    @Override
    public Optional<Crew> getCrewByCrewId(Long crewId) {
        return crewRepository.findById(crewId);
    }

    @Override
    public void updateCrew(Crew crew, CrewUpdatePutReq crewUpdatePutReq) {
        if (crewUpdatePutReq.getCrewName() != null)
            crew.setCrewName(crewUpdatePutReq.getCrewName());
        if (crewUpdatePutReq.getCrewDesc() != null)
            crew.setCrewDesc(crewUpdatePutReq.getCrewDesc());
        if (crewUpdatePutReq.getCrewJoin() != null)
            crew.setCrewJoin(Type.valueOf(crewUpdatePutReq.getCrewJoin()).getLabel());
        if (crewUpdatePutReq.getCrewImage() != null) {
            if (crew.getCrewImage() != null)
                fileProcessService.deleteImage(crew.getCrewImage());
            crew.setCrewImage(fileProcessService.uploadImage(crewUpdatePutReq.getCrewImage(), "crew"));
        }
        if (crewUpdatePutReq.getCrewAddress() != null)
            crewAddressService.updateCrewAddress(crew.getCrewId(), crewUpdatePutReq.getCrewAddress());
        crewRepository.save(crew);
    }

    @Override
    public void updateCrewNotice(Crew crew, CrewUpdatePatchReq crewUpdatePatchReq) {
        crew.setCrewNotice(crewUpdatePatchReq.getCrewNotice());
        crewRepository.save(crew);
    }

    @Override
    public void updateCrewHost(Crew crew, CrewMandatePatchReq crewMandatePatchReq) {
        crew.setUser(User.builder().userId(crewMandatePatchReq.getUserId()).build());
        crewRepository.save(crew);
    }

    @Override
    public void deleteCrew(Long crewId) {
        feedService.deleteByCrewId(crewId);
        matchService.setNullByCrewId(crewId);
        crewAddressService.deleteByCrewId(crewId);
        crewWaitingService.deleteByCrewId(crewId);
        crewRepository.delete(getCrewByCrewId(crewId).get());
    }
}
