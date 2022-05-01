package com.darly.api.service.comment;

import com.darly.db.entity.comment.Comment;
import com.darly.db.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentList(Long feedId) {
        return commentRepository.findByFeed_FeedId(feedId);
    }
}
