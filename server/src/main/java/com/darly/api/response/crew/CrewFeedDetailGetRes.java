package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import lombok.Builder;

import java.util.List;

public class CrewFeedDetailGetRes extends BaseResponseBody {
    private List<FeedDetailEntity> feeds;

    @Builder
    public CrewFeedDetailGetRes(Integer statusCode, String message, List<FeedDetailEntity> feeds) {
        super(statusCode, message);
        this.feeds = feeds;
    }
}