package com.darly.api.service.userAddress;

import com.darly.api.request.user.UserAddressPutReq;
import com.darly.db.entity.address.Address;
import com.darly.db.entity.address.AddressIdAndNameMapping;
import com.darly.db.entity.address.AddressNameMapping;
import com.darly.db.entity.user.User;
import com.darly.db.entity.userAddress.UserAddress;
import com.darly.db.entity.userAddress.UserAddressId;
import com.darly.db.repository.userAddress.UserAddressRepository;
import com.darly.db.repository.userAddress.UserAddressRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAddressService")
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    UserAddressRepositorySupport userAddressRepositorySupport;

    @Override
    public List<AddressNameMapping> getAddressNameList(Long friendId) {
        return userAddressRepositorySupport.findAddressNameList(friendId);
    }

    @Override
    public void putUserAddress(UserAddressPutReq userAddressPutReq, Long userId) {
        userAddressRepository.deleteByUserAddressId_User(User.builder().userId(userId).build());

        for (int i = 0; i < userAddressPutReq.getAddresses().size(); i++) {
            UserAddressId userAddressId = UserAddressId.builder()
                    .addressId(userAddressPutReq.getAddresses().get(i))
                    .userId(userId)
                    .build();

            UserAddress useraddress = UserAddress.builder()
                    .userAddressId(userAddressId)
                    .build();

            userAddressRepository.save(useraddress);
        }
    }

    @Override
    public void putUserAddressByStringList(List<Long> userAddresses, Long userId) {
        userAddressRepository.deleteByUserAddressId_User(User.builder().userId(userId).build());
        for (Long addressId : userAddresses) {
            userAddressRepository.save(UserAddress.builder()
                    .userAddressId(UserAddressId.builder()
                            .addressId(addressId)
                            .userId(userId)
                            .build())
                    .build());
        }
    }

    @Override
    public List<AddressIdAndNameMapping> getAddressList(Long userId) {
        return userAddressRepositorySupport.findAddressIdAndNameList(userId);
    }
}
