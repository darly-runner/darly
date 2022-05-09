package com.darly.db.repository.userAddress;

import com.darly.db.entity.address.*;
import com.darly.db.entity.user.User;
import com.darly.db.entity.userAddress.QUserAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserAddressRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QUserAddress qUserAddress = QUserAddress.userAddress;
    QAddress qAddress = QAddress.address;

    // select address_name
    // from tb_address inner join tb_user_address
    // on tb_address.address_id = tb_user_address.address_id
    // where tb_user_address.user_id = 1
    public List<AddressNameMapping> findAddressNameList(Long friendId) {
        return jpaQueryFactory.select(new QAddressNameMapping(qAddress.addressName))
                .from(qAddress).innerJoin(qUserAddress)
                .on(qAddress.addressId.eq(qUserAddress.userAddressId.address.addressId))
                .where(qUserAddress.userAddressId.user.userId.eq(friendId))
                .fetch();
    }

    public List<AddressIdAndNameMapping> findAddressIdAndNameList(Long userId) {
        return jpaQueryFactory.select(new QAddressIdAndNameMapping(qAddress.addressName, qAddress.addressId))
                .from(qAddress).innerJoin(qUserAddress)
                .on(qAddress.addressId.eq(qUserAddress.userAddressId.address.addressId))
                .where(qUserAddress.userAddressId.user.userId.eq(userId))
                .fetch();
    }
}
