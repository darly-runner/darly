package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewAddress;
import com.darly.db.entity.crew.CrewAddressId;
import com.darly.db.repository.crew.CrewAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("crewAddressService")
@RequiredArgsConstructor
public class CrewAddressServiceImpl implements CrewAddressService {
    private final CrewAddressRepository crewAddressRepository;

    @Override
    public boolean createCrewAddress(Long crewId, Long crewAddress) {
        return crewAddressRepository.save(new CrewAddress(CrewAddressId.builder().addressId(crewAddress).crewId(crewId).build())) == null ? false : true;
    }

    @Override
    public void updateCrewAddress(Long crewId, Long addressId) {
        Optional<CrewAddress> crewAddress = getCrewAddressByCrewId(crewId);
        if (crewAddress.isPresent())
            crewAddressRepository.delete(crewAddress.get());
        createCrewAddress(crewId, addressId);
    }

    @Override
    public Optional<CrewAddress> getCrewAddressByCrewId(Long crewId) {
        return crewAddressRepository.findByCrewAddressId_CrewId(crewId);
    }
}
