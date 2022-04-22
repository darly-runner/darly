package com.darly.api.service.userAddress;

import com.darly.db.entity.address.AddressNameMapping;
import com.darly.db.repository.userAddress.UserAddressRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAddressService")
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService{

    private final UserAddressRepositorySupport userAddressRepositorySupport;

    @Override
    public List<AddressNameMapping> getAddressNameList(Long friendId) {
        return userAddressRepositorySupport.findAddressNameList(friendId);
    }
}
