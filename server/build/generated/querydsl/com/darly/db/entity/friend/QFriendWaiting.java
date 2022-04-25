package com.darly.db.entity.friend;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFriendWaiting is a Querydsl query type for FriendWaiting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriendWaiting extends EntityPathBase<FriendWaiting> {

    private static final long serialVersionUID = 316675571L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriendWaiting friendWaiting = new QFriendWaiting("friendWaiting");

    public final NumberPath<Long> friendOne = createNumber("friendOne", Long.class);

    public final com.darly.db.entity.QUser friendTwo;

    public final NumberPath<Long> friendWaitingId = createNumber("friendWaitingId", Long.class);

    public QFriendWaiting(String variable) {
        this(FriendWaiting.class, forVariable(variable), INITS);
    }

    public QFriendWaiting(Path<? extends FriendWaiting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriendWaiting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriendWaiting(PathMetadata metadata, PathInits inits) {
        this(FriendWaiting.class, metadata, inits);
    }

    public QFriendWaiting(Class<? extends FriendWaiting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.friendTwo = inits.isInitialized("friendTwo") ? new com.darly.db.entity.QUser(forProperty("friendTwo")) : null;
    }

}

