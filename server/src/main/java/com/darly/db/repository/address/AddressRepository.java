package com.darly.db.repository.address;

import com.darly.db.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressesByAddressNameContains(String address);
}
