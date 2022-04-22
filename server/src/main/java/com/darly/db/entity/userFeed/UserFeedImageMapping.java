package com.darly.db.entity.userFeed;

import com.querydsl.core.annotations.QueryProjection;

public class UserFeedImageMapping {
    private String userFeedImage;

    @QueryProjection
    public UserFeedImageMapping(String userFeedImage) {
        this.userFeedImage = userFeedImage;
    }
}
