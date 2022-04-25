package com.darly.api.response.friend;

import com.darly.common.model.response.BaseResponseBody;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class FriendFeedGetRes extends BaseResponseBody {
    private Integer size; //한 페이지의 피드 수
    private Integer totalPages; //전체 페이지 수
    private Long totalFeeds; //전체 피드 수
    private Integer currentPage; //현재 페이지 번호
    private Integer numberOfFeeds; //현재 페이지의 피드 수
    private List<String> feeds;

    @Builder
    public FriendFeedGetRes(Integer statusCode, String message, Page<String> page, Integer currentPage) {
        super(statusCode, message);
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalFeeds = page.getTotalElements();
        this.currentPage = currentPage;
        this.numberOfFeeds = page.getNumberOfElements();
        this.feeds = page.getContent();
    }
}
