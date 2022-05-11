package com.darly.api.response.crew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class FeedDetailEntity {
    private String hostNickname;
    private String hostImage;
    private Long feedId;
    private String feedTitle;
    private String feedContent;
    private String feedDate;
    private String feedImage;
    private Integer commentNum;

    @Builder
    public FeedDetailEntity(String hostNickname, String hostImage, Long feedId, String feedTitle, String feedContent, Long feedDate, String feedImage, Integer commentNum) {
        this.hostNickname = hostNickname;
        this.hostImage = hostImage;
        this.feedId = feedId;
        this.feedTitle = feedTitle;
        this.feedContent = feedContent;
        this.feedDate = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(feedDate * 1000));
        this.feedImage = feedImage;
        this.commentNum = commentNum;
    }
}
