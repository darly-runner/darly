package com.darly.api.service.address;

import com.darly.db.entity.address.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddresses();
}
