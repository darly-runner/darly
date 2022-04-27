package com.darly.api.service.crew;

import com.darly.db.entity.crew.CrewAddress;

import java.util.Optional;

public interface CrewAddressService {
    boolean createCrewAddress(Long crewId, Long crewAddress);
    void updateCrewAddress(Long crewId, Long addressId);
    Optional<CrewAddress> getCrewAddressByCrewId(Long crewId);
    void deleteByCrewId(Long crewId);
}
