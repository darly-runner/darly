package com.darly.api.service.address;

import com.darly.db.entity.address.Address;
import com.darly.db.repository.address.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("AddressServiceImpl")
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    // 주소 검색
    @Override
    public List<Address> getAddresses(String address) {
        List<Address> result;

        if (address != null) {
            result = addressRepository.findAddressesByAddressNameContaining(address);
        } else {
            result = addressRepository.findAll();
        }
        return result;
    }
}
