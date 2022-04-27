package com.darly.db.repository.friend;


import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.friend.QFriend;
import com.darly.db.entity.friend.QFriendTitleMapping;
import com.darly.db.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QFriend qFriend = QFriend.friend;
    QUser qUser = QUser.user;

    public List<FriendTitleMapping> findFriendTitleList(Long userId) {
        return jpaQueryFactory.select(new QFriendTitleMapping(qUser.userId, qUser.userNickname, qUser.userMessage, qUser.userImage))
                .from(qFriend, qUser)
                .where(qFriend.friendTwo.userId.eq(qUser.userId), qFriend.friendOne.eq(userId))
                .fetch();
    }
}
