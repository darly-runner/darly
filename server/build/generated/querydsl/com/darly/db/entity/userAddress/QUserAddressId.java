package com.darly.db.entity.userAddress;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAddressId is a Querydsl query type for UserAddressId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserAddressId extends BeanPath<UserAddressId> {

    private static final long serialVersionUID = -258625045L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAddressId userAddressId = new QUserAddressId("userAddressId");

    public final com.darly.db.entity.address.QAddress address;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserAddressId(String variable) {
        this(UserAddressId.class, forVariable(variable), INITS);
    }

    public QUserAddressId(Path<? extends UserAddressId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAddressId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAddressId(PathMetadata metadata, PathInits inits) {
        this(UserAddressId.class, metadata, inits);
    }

    public QUserAddressId(Class<? extends UserAddressId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.darly.db.entity.address.QAddress(forProperty("address")) : null;
    }

}

