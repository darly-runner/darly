package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.feed.FeedDetailMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("CrewFeedGetResponse")
public class CrewFeedGetRes extends BaseResponseBody {
    private Integer size;
    private Long totalFeeds;
    private Integer currentPage;
    private Integer numberOfFeed;
    private List<FeedDetailEntity> feeds;

    @Builder
    public CrewFeedGetRes(Integer statusCode, String message, Page<FeedDetailMapping> page, Integer currentPage) {
        super(statusCode, message);
        this.size = page.getSize();
        this.totalFeeds = page.getTotalElements();
        this.currentPage = currentPage;
        this.numberOfFeed = page.getNumberOfElements();
        feeds = new ArrayList<>();
        List<FeedDetailMapping> feedMappingList = page.getContent();
        for (int i = 0; i < feedMappingList.size(); i++) {
            feeds.add(FeedDetailEntity.builder()
                    .hostNickname(feedMappingList.get(i).getHostNickname())
                    .hostImage(feedMappingList.get(i).getHostImage())
                    .feedId(feedMappingList.get(i).getFeedId())
                    .feedTitle(feedMappingList.get(i).getFeedTitle())
                    .feedContent(feedMappingList.get(i).getFeedContent())
                    .feedDate(feedMappingList.get(i).getFeedDate())
                    .feedImage(feedMappingList.get(i).getFeedImage())
                    .commentNum(feedMappingList.get(i).getCommentNum())
                    .build());
        }
    }
}
