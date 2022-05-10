package com.darly.db.repository.match;


import com.darly.db.entity.match.QUserMatch;
import com.darly.db.entity.match.UserMatch;
import com.darly.db.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserMatchRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QUserMatch qUserMatch = QUserMatch.userMatch;
    QUser qUser = QUser.user;

    public List<UserMatch> findAllByUserMatchId_Match_MatchId(Long id){
        return jpaQueryFactory.selectFrom(qUserMatch).innerJoin(qUser).on(qUserMatch.userMatchId.user.userId.eq(qUser.userId))
                .where(qUserMatch.userMatchId.match.matchId.eq(id)).fetch();
    }
}
