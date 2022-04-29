package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.feed.FeedMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("CrewFeedGetResponse")
public class CrewFeedGetRes extends BaseResponseBody {
    private Integer size;
    private Long totalFeeds;
    private Integer currentPage;
    private Integer numberOfFeed;
    private List<FeedMapping> feeds;

    @Builder
    public CrewFeedGetRes(Integer statusCode, String message, Page<FeedMapping> page, Integer currentPage) {
        super(statusCode, message);
        this.size = page.getSize();
        this.totalFeeds = page.getTotalElements();
        this.currentPage = currentPage;
        this.numberOfFeed = page.getNumberOfElements();
        this.feeds = page.getContent();
    }
}
