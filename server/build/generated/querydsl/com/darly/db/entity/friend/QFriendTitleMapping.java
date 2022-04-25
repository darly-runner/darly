package com.darly.db.entity.friend;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.darly.db.entity.friend.QFriendTitleMapping is a Querydsl Projection type for FriendTitleMapping
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFriendTitleMapping extends ConstructorExpression<FriendTitleMapping> {

    private static final long serialVersionUID = 630395760L;

    public QFriendTitleMapping(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> userNickname, com.querydsl.core.types.Expression<String> userMessage, com.querydsl.core.types.Expression<String> userImage) {
        super(FriendTitleMapping.class, new Class<?>[]{long.class, String.class, String.class, String.class}, userId, userNickname, userMessage, userImage);
    }

}

