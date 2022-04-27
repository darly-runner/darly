package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewAddress;
import com.darly.db.entity.crew.CrewAddressId;
import com.darly.db.repository.crew.CrewAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("crewAddressService")
@RequiredArgsConstructor
public class CrewAddressServiceImpl implements CrewAddressService {
    private final CrewAddressRepository crewAddressRepository;

    @Override
    public boolean createCrewAddress(Long crewId, Long crewAddress) {
        return crewAddressRepository.save(new CrewAddress(CrewAddressId.builder().addressId(crewAddress).crewId(crewId).build())) == null ? false : true;
    }
}
