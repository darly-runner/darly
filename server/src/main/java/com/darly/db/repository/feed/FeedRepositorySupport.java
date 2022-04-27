package com.darly.db.repository.feed;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeedRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
}
