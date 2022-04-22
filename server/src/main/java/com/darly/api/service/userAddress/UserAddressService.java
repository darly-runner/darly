package com.darly.api.service.userAddress;

import com.darly.db.entity.address.AddressNameMapping;

import java.util.List;

public interface UserAddressService {
    List<AddressNameMapping> getAddressNameList(Long friendId);
}
