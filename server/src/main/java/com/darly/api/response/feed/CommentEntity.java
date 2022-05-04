package com.darly.api.response.feed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentEntity {
    private Long commentId;
    private String userNickname;
    private String userImage;
    private String commentContent;
    private String commentDate;

    @Builder
    public CommentEntity(Long commentId, String userNickname, String userImage, String commentContent, String commentDate) {
        this.commentId = commentId;
        this.userNickname = userNickname;
        this.userImage = userImage;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }
}
