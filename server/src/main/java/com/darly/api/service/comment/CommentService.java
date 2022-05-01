package com.darly.api.service.comment;

import com.darly.db.entity.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getCommentList(Long feedId);
    void createComment(Long feedId, Long userId, String commentContent);
    Optional<Comment> getComment(Long commentId);
    void updateComment(Comment comment, String commentContent);
}
