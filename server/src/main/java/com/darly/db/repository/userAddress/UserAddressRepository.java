package com.darly.db.repository.userAddress;

import com.darly.db.entity.userAddress.UserAddress;
import com.darly.db.entity.userAddress.UserAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, UserAddressId> {
}
