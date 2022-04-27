package com.darly.db.repository.userAddress;

import com.darly.db.entity.user.User;
import com.darly.db.entity.userAddress.UserAddress;
import com.darly.db.entity.userAddress.UserAddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UserAddressId> {

    @Transactional
    void deleteByUserAddressId_User(User user);
}
