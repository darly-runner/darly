package com.darly.api.service.userAddress;

import com.darly.api.request.user.UserAddressPutReq;
import com.darly.db.entity.address.Address;
import com.darly.db.entity.address.AddressIdAndNameMapping;
import com.darly.db.entity.address.AddressNameMapping;

import java.util.List;

public interface UserAddressService {
    List<AddressNameMapping> getAddressNameList(Long friendId);

    void putUserAddress(UserAddressPutReq userAddressPutReq, Long userId);

    void putUserAddressByStringList(List<Long> userAddresses, Long userId);

    List<AddressIdAndNameMapping> getAddressList(Long userId);
}
