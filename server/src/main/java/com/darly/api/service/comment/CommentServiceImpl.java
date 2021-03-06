package com.darly.api.service.comment;

import com.darly.db.entity.comment.Comment;
import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.user.User;
import com.darly.db.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentList(Long feedId) {
        return commentRepository.findByFeed_FeedId(feedId);
    }

    @Override
    public void createComment(Long feedId, Long userId, String commentContent) {
        commentRepository.save(Comment.builder()
                .user(User.builder().userId(userId).build())
                .feed(Feed.builder().feedId(feedId).build())
                .commentContent(commentContent)
                .commentDate(getTimestamp())
                .build());
    }

    @Override
    public Optional<Comment> getComment(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void updateComment(Comment comment, String commentContent) {
        comment.setCommentContent(commentContent);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    private Long getTimestamp() {
        return Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime() / 1000;
    }

}
