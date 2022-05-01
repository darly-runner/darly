package com.darly.api.response.feed;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.comment.Comment;
import com.darly.db.entity.feed.Feed;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("FeedDetailGetResponse")
public class FeedDetailGetRes extends BaseResponseBody {
    private String hostNickname;
    private String hostImage;
    private String feedTitle;
    private String feedContent;
    private String feedDate;
    private List<String> feedImages;
    private List<CommentEntity> commentList;

    @Builder
    public FeedDetailGetRes(Integer statusCode, String message, Feed feed, List<Comment> commentList, List<String> feedImages) {
        super(statusCode, message);
        this.hostNickname = feed.getUser().getUserNickname();
        this.hostImage = feed.getUser().getUserImage();
        this.feedTitle = feed.getFeedTitle();
        this.feedContent = feed.getFeedContent();
        this.feedDate = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(feed.getFeedDate() * 1000));
        this.feedImages = feedImages;
        this.commentList = new ArrayList<>();
        for (Comment comment : commentList) {
            this.commentList.add(CommentEntity.builder()
                    .commentId(comment.getCommentId())
                    .userNickname(comment.getUser().getUserNickname())
                    .userImage(comment.getUser().getUserImage())
                    .commentContent(comment.getCommentContent())
                    .commentDate(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(comment.getCommentDate() * 1000)))
                    .build());
        }
    }
}
