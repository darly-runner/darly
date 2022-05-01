package com.darly.api.service.comment;

import com.darly.db.entity.comment.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentList(Long feedId);
}
