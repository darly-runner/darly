package com.darly.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 464125823L;

    public static final QUser user = new QUser("user");

    public final StringPath userEmail = createString("userEmail");

    public final NumberPath<Float> userGoalDistance = createNumber("userGoalDistance", Float.class);

    public final NumberPath<Integer> userGoalTime = createNumber("userGoalTime", Integer.class);

    public final NumberPath<Integer> userHeartNum = createNumber("userHeartNum", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userImage = createString("userImage");

    public final StringPath userMessage = createString("userMessage");

    public final NumberPath<Float> userMinPace = createNumber("userMinPace", Float.class);

    public final StringPath userNickname = createString("userNickname");

    public final NumberPath<Integer> userPoint = createNumber("userPoint", Integer.class);

    public final NumberPath<Integer> userTotalCalories = createNumber("userTotalCalories", Integer.class);

    public final NumberPath<Float> userTotalDistance = createNumber("userTotalDistance", Float.class);

    public final NumberPath<Integer> userTotalHeart = createNumber("userTotalHeart", Integer.class);

    public final NumberPath<Float> userTotalPace = createNumber("userTotalPace", Float.class);

    public final NumberPath<Long> userTotalTime = createNumber("userTotalTime", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

