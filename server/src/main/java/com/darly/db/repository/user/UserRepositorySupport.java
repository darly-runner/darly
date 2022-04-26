package com.darly.db.repository.user;

;
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
public class UserRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;
    QFriend qFriend = QFriend.friend;

    //    select u.user_id, u.user_nickname
    //    from tb_user u
    //    left join tb_friend f on (f.friend_one = 6 and u.user_id = f.friend_two)
    //    where f.friend_two is null
    //    and u.user_nickname like "%o%"
    //    and user_id != 6;
    public List<FriendTitleMapping> findUserTitleSearchList(Long userId, String nickname) {
        return jpaQueryFactory.select(new QFriendTitleMapping(qUser.userId, qUser.userNickname, qUser.userMessage, qUser.userImage))
                .from(qUser)
                .leftJoin(qFriend).on(qFriend.friendOne.eq(userId), qUser.userId.eq(qFriend.friendTwo.userId))
                .where(qFriend.friendTwo.isNull(), qUser.userNickname.contains(nickname), qUser.userId.ne(userId))
                .fetch();
    }

}
