package com.darly.db.repository.friend;


import com.darly.db.entity.friend.FriendTitleMapping;
import com.darly.db.entity.friend.QFriendTitleMapping;
import com.darly.db.entity.friend.QFriendWaiting;
import com.darly.db.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendWaitingRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QFriendWaiting qFriendWaiting = QFriendWaiting.friendWaiting;
    QUser qUser = QUser.user;

    public List<FriendTitleMapping> findFriendWaitingTitleList(Long userId) {
        return jpaQueryFactory.select(new QFriendTitleMapping(qUser.userId, qUser.userNickname, qUser.userMessage, qUser.userImage))
                .from(qFriendWaiting, qUser)
                .where(qFriendWaiting.friendTwo.userId.eq(qUser.userId), qFriendWaiting.friendOne.userId.eq(userId))
                .fetch();
    }
}
