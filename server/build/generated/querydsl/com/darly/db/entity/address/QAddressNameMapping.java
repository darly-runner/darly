package com.darly.db.entity.address;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.darly.db.entity.address.QAddressNameMapping is a Querydsl Projection type for AddressNameMapping
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAddressNameMapping extends ConstructorExpression<AddressNameMapping> {

    private static final long serialVersionUID = 942619677L;

    public QAddressNameMapping(com.querydsl.core.types.Expression<String> addressName) {
        super(AddressNameMapping.class, new Class<?>[]{String.class}, addressName);
    }

}

